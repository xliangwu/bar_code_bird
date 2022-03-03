package com.caveup.barcode.helper;

import com.caveup.barcode.constants.Constants;
import com.caveup.barcode.constants.InterpolateType;
import com.caveup.barcode.constants.PrintType;
import com.caveup.barcode.entity.*;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.DashedBorder;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author xw80329
 */
@Slf4j
public class PdfHelper {

    private static byte[] JOIN_IMG_CONTENT = null;

    static {
        try (ByteArrayOutputStream buffer = new ByteArrayOutputStream();
             BufferedInputStream input = new BufferedInputStream(PdfHelper.class.getClassLoader().getResourceAsStream("jietou.png"))) {

            int nRead;
            byte[] data = new byte[4096];
            while ((nRead = input.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            JOIN_IMG_CONTENT = buffer.toByteArray();
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    /**
     * 生成 pdf 待打印的文档
     *
     * @param htmlTableTemplate
     * @param params
     * @param startIndex
     * @param endIndex
     * @param printType
     */
    public static Optional<String> generatePrintPdf(HtmlTable htmlTableTemplate,
                                                    Map<String, Object> params,
                                                    int startIndex,
                                                    int endIndex,
                                                    PrintType printType) {

        try {
            File output = File.createTempFile("data_", ".pdf");
            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(output));

            int tableCountOfOnePage = 4;
            if (printType == PrintType.P2_2) {
                pdfDoc.setDefaultPageSize(PageSize.A4.rotate());
                tableCountOfOnePage = 4;
            } else if (printType == PrintType.P2_3) {
                pdfDoc.setDefaultPageSize(PageSize.A4);
                tableCountOfOnePage = 6;
            }

            Document document = new Document(pdfDoc);
            Table pdfTable = createPageTable(htmlTableTemplate);
            int index = 1;
            boolean hasCells = false;
            int lastRow = (endIndex - startIndex) % 2 == 0 ? (endIndex - startIndex) / 2 : (endIndex - startIndex) / 2 + 1;
            for (int i = startIndex; i < endIndex; i++, index++) {
                params.put("index", i);
                boolean pageBottomRow = (index + 1) % tableCountOfOnePage == 0 || index % tableCountOfOnePage == 0;
                boolean isLastRow = (lastRow == index / 2) || (lastRow == (index + 1) / 2);
                Table table = createPdfTable(htmlTableTemplate, params, i);
                Cell pdfCell = new Cell().add(table);
                pdfCell.setPaddings(ObjectsHelper.nvl(htmlTableTemplate.getPaddingTop(), 8),
                        ObjectsHelper.nvl(htmlTableTemplate.getPaddingRight(), 8),
                        ObjectsHelper.nvl(htmlTableTemplate.getPaddingBottom(), 8),
                        ObjectsHelper.nvl(htmlTableTemplate.getPaddingLeft(), 8));
                pdfCell.setBorder(Border.NO_BORDER);
                pdfCell.setBorderBottom(pageBottomRow || isLastRow ? Border.NO_BORDER : new DashedBorder(1));
                pdfCell.setBorderRight(index % 2 == 0 ? Border.NO_BORDER : new DashedBorder(1));
                pdfCell.setHorizontalAlignment(HorizontalAlignment.CENTER);
                pdfCell.setVerticalAlignment(VerticalAlignment.MIDDLE);
                pdfTable.addCell(pdfCell);
                hasCells = true;
                log.info("process #{} table,index:{}", i, index);
                if (index != 0 && index % tableCountOfOnePage == 0) {
                    document.add(pdfTable);
                    document.add(new AreaBreak());
                    pdfTable = createPageTable(htmlTableTemplate);
                    hasCells = false;
                    log.info("add new table,index:{}", index);
                }
            }

            //add last table
            if (hasCells) {
                log.info("add last table,index:{}", index);
                document.add(pdfTable);
            }
            document.close();
            return Optional.of(output.getAbsolutePath());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    private static Table createPageTable(HtmlTable htmlTableTemplate) {
        Table pdfTable = new Table(2);
        pdfTable.setHorizontalAlignment(HorizontalAlignment.CENTER);
        int percentWidth = Objects.isNull(htmlTableTemplate.getPercentWidth()) ? 100 : htmlTableTemplate.getPercentWidth();
        pdfTable.setWidth(new UnitValue(2, percentWidth));
        return pdfTable;
    }

    private static Table createPdfTable(HtmlTable htmlTableTemplate,
                                        Map<String, Object> params,
                                        int index) throws IOException {
        params.put("index", index);
        int maxCols = htmlTableTemplate.getRows().stream().mapToInt(TableRow::calculateCols).reduce(Integer::max).getAsInt();
        Table pdfTable = new Table(maxCols);
        PdfFont font = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H", true);
        pdfTable.setFont(font);
        pdfTable.setAutoLayout();
        pdfTable.setFontSize(htmlTableTemplate.getFontSize());
        log.info("total cols:{}", maxCols);
        String qrText = null;
        for (TableRow row : htmlTableTemplate.getRows()) {
            for (TableCell cell : row.getCells()) {
                String content = interpolate(cell.getText(), cell.getInterpolate(), params);
                log.info("rowSpan:{},colSpan:{},{} =>{}", cell.getRowSpan(), cell.getColSpan(), cell.getText(), content);
                Cell pdfCell = new Cell(cell.getRowSpan(), cell.getColSpan());
                int cellWidth = Math.max(cell.getWidth(), 6);
                pdfCell.setBorder(new SolidBorder(Border.SOLID));
                pdfCell.setFontSize(cell.getFontSize());
                pdfCell.setVerticalAlignment(VerticalAlignment.valueOf(ObjectsHelper.nvl(cell.getVerticalAlignment(), CssVerticalAlignment.MIDDLE).name()));
                pdfCell.setTextAlignment(TextAlignment.valueOf(ObjectsHelper.nvl(cell.getAlignment(), CssTextAlignment.CENTER).name()));
                pdfCell.setPaddings(ObjectsHelper.nvl(cell.getPaddingTop(), 1),
                        ObjectsHelper.nvl(cell.getPaddingRight(), 1),
                        ObjectsHelper.nvl(cell.getPaddingBottom(), 1),
                        ObjectsHelper.nvl(cell.getPaddingLeft(), 1));

                if (InterpolateType.QR_CODE == cell.getInterpolate().getType()) {
                    int qrWith = Math.max(70, cellWidth - 6);
                    qrText = content;
                    byte[] contents = QrCodeHelper.createQrCodeData(content, qrWith, qrWith);
                    if (null != contents) {
                        ImageData imageData = ImageDataFactory.create(contents);
                        Image qrCodeImg = new Image(imageData);
                        qrCodeImg.setMinWidth(75);
                        pdfCell.setWidth(qrWith);
                        pdfCell.setVerticalAlignment(VerticalAlignment.MIDDLE);
                        pdfCell.setHorizontalAlignment(HorizontalAlignment.CENTER);
                        pdfCell.setTextAlignment(TextAlignment.CENTER);
                        pdfCell.add(qrCodeImg);
                    }
                } else if (InterpolateType.JOINT_IMG == cell.getInterpolate().getType()) {
                    if (null != JOIN_IMG_CONTENT) {
                        ImageData imageData = ImageDataFactory.create(JOIN_IMG_CONTENT);
                        Image qrCodeImg = new Image(imageData);
                        qrCodeImg.setAutoScale(true);
                        qrCodeImg.setMaxWidth(260);
                        pdfCell.add(qrCodeImg);
                    }
                } else {
                    boolean isBold = Constants.FONT_WEIGHT_BOLD.equals(cell.getFontWeight());
                    String[] lines = content.split(Constants.PDF_NEW_LINE);
                    int j = 0;
                    for (String line : lines) {
                        Paragraph paragraph = new Paragraph(line);
                        if (isBold || (j == 0 && lines.length > 1)) {
                            paragraph.setFontSize(18);
                            paragraph.setBold();
                        }
                        pdfCell.add(paragraph);
                        j++;
                    }
                    pdfCell.setWidth(cell.getWidth());
                }
                pdfTable.addCell(pdfCell);
            }
        }

        //last
        if (null != qrText) {
            Cell lastCell = new Cell(1, maxCols);
            lastCell.setTextAlignment(TextAlignment.RIGHT);
            lastCell.setPaddingRight(4);
            lastCell.setPaddingTop(0);
            lastCell.add(new Paragraph(qrText));
            lastCell.setBorderBottom(Border.NO_BORDER);
            lastCell.setBorderRight(Border.NO_BORDER);
            lastCell.setBorderLeft(Border.NO_BORDER);
            lastCell.setFontSize(8);
            pdfTable.addCell(lastCell);
        }
        return pdfTable;
    }

    private static String interpolate(String originText, InterpolateEntity type, Map<String, Object> paramsMap) {
        return type.getType().interpolate(originText, type.getFormat(), type.getParamKeys(), paramsMap);
    }
}
