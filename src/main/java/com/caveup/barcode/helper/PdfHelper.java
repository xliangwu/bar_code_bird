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
import java.util.Optional;

/**
 * @author xw80329
 * @link https://www.cl.cam.ac.uk/~mgk25/iso-paper-ps.txt
 */
@Slf4j
public class PdfHelper {

    private static byte[] JOIN_IMG_CONTENT = null;
    private static final int A4_WIDTH = 595;
    private static final int A4_HEIGHT = 842;

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
            int colsOfRow = 2;
            pdfDoc.setDefaultPageSize(PageSize.A4.rotate());
            if (printType == PrintType.P2_3) {
                tableCountOfOnePage = 6;
                colsOfRow = 2;
            }

            Document document = new Document(pdfDoc);
            document.setHorizontalAlignment(HorizontalAlignment.CENTER);
            Table pdfTable = createPageTable(colsOfRow);
            int index = 1;
            boolean hasCells = false;
            int lastRowIndex = (endIndex - startIndex) % colsOfRow == 0 ? (endIndex - startIndex) / colsOfRow : (endIndex - startIndex) / colsOfRow + 1;
            for (int i = startIndex; i < endIndex; i++, index++) {
                params.put("index", i);
                log.debug("index:{}", index);
                boolean pageBottomRow = index % tableCountOfOnePage == 0 || index % tableCountOfOnePage >= printType.getPageBottomIndex();
                boolean isLastRow = lastRowIndex == ((index - 1) / colsOfRow + 1);
                Table table = createPdfTable(htmlTableTemplate, params, i, printType);
                Cell pdfCell = new Cell().add(table);
                pdfCell.setPaddings(ObjectsHelper.max(htmlTableTemplate.getPaddingTop(), printType.getOutTablePaddingTop()),
                        ObjectsHelper.max(htmlTableTemplate.getPaddingRight(), 8),
                        ObjectsHelper.max(htmlTableTemplate.getPaddingBottom(), printType.getOutTablePaddingTop()),
                        ObjectsHelper.max(htmlTableTemplate.getPaddingLeft(), 8));
                pdfCell.setBorder(Border.NO_BORDER);
                pdfCell.setBorderBottom(pageBottomRow || isLastRow ? Border.NO_BORDER : new DashedBorder(1));
                //最右边且不是最后一个
                pdfCell.setBorderRight(index % colsOfRow == 0 || index == endIndex - 1 ? Border.NO_BORDER : new DashedBorder(1));
                pdfCell.setHorizontalAlignment(HorizontalAlignment.CENTER);
                pdfCell.setVerticalAlignment(VerticalAlignment.MIDDLE);
                pdfTable.addCell(pdfCell);
                hasCells = true;
                log.info("process #{} table,index:{}", i, index);
                if (index % tableCountOfOnePage == 0 && index != endIndex - 1) {
                    document.add(pdfTable);
                    document.add(new AreaBreak());
                    pdfTable = createPageTable(colsOfRow);
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

    private static Table createPageTable(int colsOfRow) {
        Table pdfTable = new Table(colsOfRow);
        pdfTable.setHorizontalAlignment(HorizontalAlignment.CENTER);
        pdfTable.setTextAlignment(TextAlignment.CENTER);
        pdfTable.setWidth(new UnitValue(2, 100));
        return pdfTable;
    }

    private static Table createPdfTable(HtmlTable htmlTableTemplate,
                                        Map<String, Object> params,
                                        int index, PrintType printType) throws IOException {
        params.put("index", index);
        int maxCols = htmlTableTemplate.getRows().stream().mapToInt(TableRow::calculateCols).reduce(Integer::max).getAsInt();
        Table pdfTable = new Table(maxCols);
        PdfFont font = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H", true);
        pdfTable.setFont(font);
        pdfTable.setAutoLayout();
        //每行只有2列
        pdfTable.setWidth(A4_HEIGHT / 2 - 60);
        pdfTable.setFontSize(printType.getFontSize());
        pdfTable.setMarginTop(2);
        log.info("total cols:{}", maxCols);
        String qrText = null;
        for (TableRow row : htmlTableTemplate.getRows()) {
            for (TableCell cell : row.getCells()) {
                String content = interpolate(cell.getText(), cell.getInterpolate(), params);
                log.info("rowSpan:{},colSpan:{},{} =>{}", cell.getRowSpan(), cell.getColSpan(), cell.getText(), content);
                Cell pdfCell = new Cell(cell.getRowSpan(), cell.getColSpan());
                int cellWidth = Math.max(cell.getWidth(), 6);
                pdfCell.setBorder(new SolidBorder(Border.SOLID));
                pdfCell.setVerticalAlignment(VerticalAlignment.valueOf(ObjectsHelper.nvl(cell.getVerticalAlignment(), CssVerticalAlignment.MIDDLE).name()));
                pdfCell.setTextAlignment(TextAlignment.valueOf(ObjectsHelper.nvl(cell.getAlignment(), CssTextAlignment.CENTER).name()));
                pdfCell.setPaddings(ObjectsHelper.nvl(cell.getPaddingTop(), 1),
                        ObjectsHelper.nvl(cell.getPaddingRight(), 2),
                        ObjectsHelper.nvl(cell.getPaddingBottom(), 1),
                        ObjectsHelper.nvl(cell.getPaddingLeft(), 2));

                if (InterpolateType.QR_CODE == cell.getInterpolate().getType()) {
                    int cellHeight = cell.getRowSpan() >= 5 ? 15 : 17;
                    int rowHeight = printType == PrintType.P2_2 ? cell.getRowSpan() * 22 : cell.getRowSpan() * cellHeight;
                    qrText = content;
                    int qrHeight = rowHeight - 4;
                    byte[] contents = QrCodeHelper.createQrCodeData(content, qrHeight, qrHeight);
                    if (null != contents) {
                        log.info("qr row height:{},img height:{}", rowHeight, qrHeight);
                        ImageData imageData = ImageDataFactory.create(contents);
                        Image qrCodeImg = new Image(imageData);
                        qrCodeImg.setMarginTop(printType.getQrCodePadding());
                        qrCodeImg.setMarginBottom(printType.getQrCodePadding());
                        qrCodeImg.scaleToFit(qrHeight, qrHeight);
                        pdfCell.add(qrCodeImg);
                        pdfCell.setVerticalAlignment(VerticalAlignment.BOTTOM);
                        pdfCell.setHorizontalAlignment(HorizontalAlignment.RIGHT);
                        pdfCell.setTextAlignment(TextAlignment.CENTER);
                        pdfCell.setHeight(rowHeight);
//                        pdfCell.setBackgroundColor(new DeviceRgb(255, 0, 0));
                        pdfCell.setWidth(rowHeight + 10);
                    }
                } else if (InterpolateType.JOINT_IMG == cell.getInterpolate().getType()) {
                    if (null != JOIN_IMG_CONTENT) {
                        ImageData imageData = ImageDataFactory.create(JOIN_IMG_CONTENT);
                        Image qrCodeImg = new Image(imageData);
                        qrCodeImg.setHeight(24);
                        qrCodeImg.setWidth(new UnitValue(2, 100));
                        pdfCell.setWidth(cell.getWidth());
                        pdfCell.add(qrCodeImg);
                    }
                } else {
                    boolean isBold = Constants.FONT_WEIGHT_BOLD.equals(cell.getFontWeight());
                    String[] lines = content.split(Constants.PDF_NEW_LINE);
                    int j = 0;
                    for (String line : lines) {
                        Paragraph paragraph = new Paragraph(line);
                        if (isBold || (j == 0 && lines.length > 1)) {
                            paragraph.setFontSize(printType.getFontSize() + 2);
                            paragraph.setBold();
                        }
                        pdfCell.add(paragraph);
                        j++;
                    }
                    pdfCell.setWidth(cellWidth);
                }
                log.info("cell width:{},height:{}", pdfCell.getWidth(), pdfCell.getHeight());
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
