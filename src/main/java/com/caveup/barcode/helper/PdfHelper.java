package com.caveup.barcode.helper;

import com.caveup.barcode.constants.Constants;
import com.caveup.barcode.constants.InterpolateType;
import com.caveup.barcode.constants.PrintType;
import com.caveup.barcode.entity.*;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author xw80329
 * @link https://www.cl.cam.ac.uk/~mgk25/iso-paper-ps.txt
 */
@Slf4j
public class PdfHelper {

    public static final String C_WINDOWS_FONTS_SIMHEI_TTF = "C:/Windows/Fonts/simhei.ttf";
    private static final String PRODUCT_CODE = "productCode";

    private static byte[] JOIN_IMG_CONTENT = null;
    private static final int A4_WIDTH = 595;
    private static final int A4_HEIGHT = 842;
    private static boolean simheiFontExist = false;

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
            simheiFontExist = new File(C_WINDOWS_FONTS_SIMHEI_TTF).exists();

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
            int tableHeight = A4_WIDTH / 2 - 4;
            pdfDoc.setDefaultPageSize(PageSize.A4.rotate());
            if (printType == PrintType.P2_3) {
                tableCountOfOnePage = 6;
                colsOfRow = 2;
                tableHeight = A4_WIDTH / 3 - 2;
            }

            Document document = new Document(pdfDoc);
            document.setHorizontalAlignment(HorizontalAlignment.CENTER);
            PdfFont font = simheiFontExist ? PdfFontFactory.createFont(C_WINDOWS_FONTS_SIMHEI_TTF, PdfEncodings.IDENTITY_H, true)
                    : PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H", true);
            document.setFont(font);
            document.setBottomMargin(2);
            document.setTopMargin(4);
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
                int paddingTop = ObjectsHelper.max(htmlTableTemplate.getPaddingTop(), printType.getOutTablePaddingTop());
                int paddingBottom = ObjectsHelper.max(htmlTableTemplate.getPaddingBottom(), printType.getOutTablePaddingTop());
                pdfCell.setPaddings(paddingTop,
                        ObjectsHelper.max(htmlTableTemplate.getPaddingRight(), 8),
                        paddingBottom,
                        ObjectsHelper.max(htmlTableTemplate.getPaddingLeft(), 8));
                pdfCell.setBorder(Border.NO_BORDER);
                pdfCell.setHeight(tableHeight - paddingBottom - paddingTop);
                pdfCell.setMaxHeight(tableHeight - paddingBottom - paddingTop);
//                pdfCell.setBorderBottom(pageBottomRow || isLastRow ? Border.NO_BORDER : new DashedBorder(1));
                //最右边且不是最后一个
//                pdfCell.setBorderRight(index % colsOfRow == 0 || index == endIndex - 1 ? Border.NO_BORDER : new DashedBorder(1));
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
        pdfTable.setAutoLayout();
        boolean productCodeEmpty = StringUtils.isBlank((String) params.get(PRODUCT_CODE));
        //每行只有2列
        pdfTable.setWidth(A4_HEIGHT / 2 - 60);
        pdfTable.setFontSize(printType.getFontSize());
        if (printType == PrintType.P2_2 && Constants.DEFAULT_FONT_SIZE != htmlTableTemplate.getF1FontSize()) {
            pdfTable.setFontSize(htmlTableTemplate.getF1FontSize());
        }
        if (printType == PrintType.P2_3 && Constants.DEFAULT_FONT_SIZE != htmlTableTemplate.getF1FontSize()) {
            pdfTable.setFontSize(htmlTableTemplate.getF2FontSize());
        }

        pdfTable.setMarginTop(2);
        pdfTable.setHeight(new UnitValue(2, 100));
        log.info("total cols:{}", maxCols);
        String qrText = null;
        for (TableRow row : htmlTableTemplate.getRows()) {
            for (TableCell cell : row.getCells()) {
                String content = interpolate(cell.getText(), cell.getInterpolate(), params);
                log.info("rowSpan:{},colSpan:{},{} =>{}", cell.getRowSpan(), cell.getColSpan(), cell.getText(), content);
                Cell pdfCell = new Cell(cell.getRowSpan(), cell.getColSpan());
                int cellWidth = Math.max(cell.getWidth(), 6);
                pdfCell.setBorder(new SolidBorder(1.3f));
                pdfCell.setVerticalAlignment(VerticalAlignment.valueOf(ObjectsHelper.nvl(cell.getVerticalAlignment(), CssVerticalAlignment.MIDDLE).name()));
                pdfCell.setTextAlignment(TextAlignment.valueOf(ObjectsHelper.nvl(cell.getAlignment(), CssTextAlignment.CENTER).name()));
                pdfCell.setPaddings(ObjectsHelper.nvl(cell.getPaddingTop(), 0),
                        ObjectsHelper.nvl(cell.getPaddingRight(), 2),
                        ObjectsHelper.nvl(cell.getPaddingBottom(), 0),
                        ObjectsHelper.nvl(cell.getPaddingLeft(), 2));
                pdfCell.setMargins(0.0f, 0, 0, 0);

                if (InterpolateType.QR_CODE == cell.getInterpolate().getType()) {
                    int cellHeight = cell.getRowSpan() >= 5 ? 20 : 18;
                    int rowHeight = printType == PrintType.P2_2 ? cell.getRowSpan() * 24 : cell.getRowSpan() * cellHeight;
                    qrText = content;
                    int qrHeight = rowHeight - 2;
                    byte[] contents = QrCodeHelper.createQrCodeData(content, qrHeight, qrHeight);
                    if (null != contents) {
                        log.info("qr row height:{},img height:{}", rowHeight, qrHeight);
                        ImageData imageData = ImageDataFactory.create(contents);
                        Image qrCodeImg = new Image(imageData);
                        qrCodeImg.setMarginTop(printType.getQrCodePadding());
                        qrCodeImg.setMarginBottom(printType.getQrCodePadding());
                        qrCodeImg.scaleToFit(qrHeight, qrHeight);
                        pdfCell.add(qrCodeImg);
                        pdfCell.setHeight(rowHeight);
                        pdfCell.setMaxWidth(rowHeight + 16);
                        pdfCell.setWidth(rowHeight + 16);
                        pdfCell.setVerticalAlignment(VerticalAlignment.MIDDLE);
                        pdfCell.setHorizontalAlignment(HorizontalAlignment.CENTER);
                        pdfCell.setTextAlignment(TextAlignment.CENTER);
                    }
                } else if (InterpolateType.JOINT_IMG == cell.getInterpolate().getType()) {
                    if (null != JOIN_IMG_CONTENT) {
                        Paragraph paragraph = new Paragraph("接头位置");
                        paragraph.setWidth(70);
                        Cell paragraphCell = new Cell(1, 1);
                        paragraphCell.setTextAlignment(TextAlignment.RIGHT);
                        paragraphCell.setPaddings(0, 0, 0, 0);
                        paragraphCell.setMargins(0, 0, 0, 0);
                        paragraphCell.setBorder(Border.NO_BORDER);
                        paragraphCell.add(paragraph);
                        ImageData imageData = ImageDataFactory.create(JOIN_IMG_CONTENT);
                        Image joinImg = new Image(imageData);
                        joinImg.setHeight(16);
                        joinImg.setWidth(140);
                        joinImg.setMaxWidth(140);
                        Cell joinCell = new Cell(1, 1);
                        joinCell.setPaddings(0, 0, 0, 4);
                        joinCell.setMargins(0, 0, 0, 0);
                        joinCell.setBorder(Border.NO_BORDER);
                        joinCell.add(joinImg);

                        Table table = new Table(2);
                        table.addCell(paragraphCell);
                        table.addCell(joinCell);
                        table.setBorder(Border.NO_BORDER);
                        table.setPaddings(2, 0, 0, 0);
                        table.setMaxWidth(240);
                        table.setWidth(240);
                        pdfCell.setWidth(cell.getWidth());
                        pdfCell.add(table);
                    }
                } else {
                    boolean isBold = Constants.FONT_WEIGHT_BOLD.equals(cell.getFontWeight());
                    String[] lines = content.split(Constants.PDF_NEW_LINE);
                    int j = 0;

                    java.util.List<String> newLines = Stream.of(lines).filter(StringUtils::isNoneBlank).collect(Collectors.toList());
                    for (String line : newLines) {
                        Paragraph paragraph = new Paragraph(line);

                        if (Constants.DEFAULT_FONT_SIZE != cell.getFontSize()) {
                            paragraph.setFontSize(cell.getFontSize());
                        }

                        paragraph.setMarginTop(0.0f);
                        paragraph.setMarginBottom(0.0f);
                        paragraph.setPaddings(0.0f, 0.0f, 0.0f, 0.0f);
                        boolean headerLine = j == 0 && newLines.size() > 1;
                        if (headerLine || newLines.size() == 1) {
                            log.info("default header font size:{}", cell.getFontSize());
                            /**
                             * 只有第一行 且 2x2格式
                             */
                            if (headerLine && printType == PrintType.P2_2 && Constants.DEFAULT_FONT_SIZE != cell.getP1HeaderFontSize()) {
                                paragraph.setFontSize(cell.getP1HeaderFontSize());
                            } else if (Constants.DEFAULT_FONT_SIZE != cell.getP2HeaderFontSize()) {
                                paragraph.setFontSize(cell.getP2HeaderFontSize());
                            }

                            if (isBold) {
                                paragraph.setBold();
                            }
                        }

                        if (printType == PrintType.P2_2) {
                            paragraph.setMultipliedLeading(1.0f);
                        } else {
                            paragraph.setMultipliedLeading(0.8f);
                        }
                        pdfCell.add(paragraph);
                        j++;
                    }
                    pdfCell.setWidth(cellWidth);

                    if (Constants.DEFAULT_FONT_SIZE != cell.getHeight()) {
                        int bufferHeight = productCodeEmpty && printType == PrintType.P2_2 ? 4 : 0;
                        pdfCell.setHeight(cell.getHeight() + bufferHeight);
                        pdfCell.setMaxHeight(cell.getHeight() + bufferHeight);
                        log.info("set customer height:{}", cell.getHeight());
                    }
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
            lastCell.setMargins(0.0f, 0.0f, 0.0f, 0.0f);
            lastCell.add(new Paragraph(qrText));
            lastCell.setBorderBottom(Border.NO_BORDER);
            lastCell.setBorderRight(Border.NO_BORDER);
            lastCell.setBorderLeft(Border.NO_BORDER);
            lastCell.setFontSize(printType.getFontSize() - 1);
            pdfTable.addCell(lastCell);
        }
        return pdfTable;
    }

    private static String interpolate(String originText, InterpolateEntity type, Map<String, Object> paramsMap) {
        return type.getType().interpolate(originText, type.getFormat(), type.getParamKeys(), paramsMap);
    }
}
