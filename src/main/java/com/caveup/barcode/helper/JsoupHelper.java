package com.caveup.barcode.helper;

import com.caveup.barcode.constants.Constants;
import com.caveup.barcode.constants.CssAttribute;
import com.caveup.barcode.constants.InterpolateType;
import com.caveup.barcode.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Optional;
import java.util.StringTokenizer;

/**
 * @author xw80329
 */
@Slf4j
public class JsoupHelper {

    public static Optional<HtmlTable> parseTable(String template) {
        if (StringUtils.isBlank(template)) {
            return Optional.empty();
        }

        Document document = Jsoup.parse(template);
        Elements tables = document.getElementsByTag("table");
        if (CollectionUtils.isEmpty(tables)) {
            log.info("no table in given template:{}", template);
            return Optional.empty();
        }
        HtmlTable htmlTable = new HtmlTable();
        Element tableEle = tables.get(0);
        htmlTable.setPaddingTop(parseCssAttribute(tableEle, CssAttribute.PADDING_TOP, Constants.DEFAULT_PADDING));
        htmlTable.setPaddingRight(parseCssAttribute(tableEle, CssAttribute.PADDING_RIGHT, Constants.DEFAULT_PADDING));
        htmlTable.setPaddingBottom(parseCssAttribute(tableEle, CssAttribute.PADDING_BOTTOM, Constants.DEFAULT_PADDING));
        htmlTable.setPaddingLeft(parseCssAttribute(tableEle, CssAttribute.PADDING_LEFT, Constants.DEFAULT_PADDING));
        htmlTable.setPercentWidth(parseCssAttribute(tableEle, CssAttribute.WIDTH, 100));
        htmlTable.setFontSize(parseCssAttribute(tableEle, CssAttribute.FONT_SIZE, Constants.DEFAULT_FONT_SIZE));

        Elements trs = tableEle.getElementsByTag("tr");
        for (Element tr : trs) {
            TableRow row = new TableRow();
            Elements tds = tr.getElementsByTag("td");
            if (CollectionUtils.isEmpty(tds)) {
                tds = tr.getElementsByTag("th");
            }

            for (Element td : tds) {
                String text = td.text();
                int colSpan = parseInt(td.attr("colSpan"), 1);
                int rowSpan = parseInt(td.attr("rowSpan"), 1);
                TableCell cell = new TableCell(text, colSpan, rowSpan);
                cell.setInterpolate(parseInterpolate(text));
                cell.setPaddingTop(parseCssAttribute(td, CssAttribute.PADDING_TOP, Constants.DEFAULT_PADDING));
                cell.setPaddingRight(parseCssAttribute(td, CssAttribute.PADDING_RIGHT, Constants.DEFAULT_PADDING));
                cell.setPaddingBottom(parseCssAttribute(td, CssAttribute.PADDING_BOTTOM, Constants.DEFAULT_PADDING));
                cell.setPaddingLeft(parseCssAttribute(td, CssAttribute.PADDING_LEFT, Constants.DEFAULT_PADDING));
                cell.setFontSize(parseCssAttribute(td, CssAttribute.FONT_SIZE, Constants.DEFAULT_FONT_SIZE));
                cell.setWidth(parseCssAttribute(td, CssAttribute.WIDTH, -1));
                cell.setAlignment(CssTextAlignment.valueOf(parseCssAttribute(td, CssAttribute.TEXT_ALIGN, CssTextAlignment.LEFT.name()).toUpperCase()));
                cell.setVerticalAlignment(CssVerticalAlignment.valueOf(parseCssAttribute(td, CssAttribute.VERTICAL_ALIGN, CssVerticalAlignment.MIDDLE.name()).toUpperCase()));
                row.addCell(cell);
            }
            htmlTable.addRow(row);
        }
        return Optional.of(htmlTable);
    }

    /**
     * $DATE$yyyy-MM-dd$selectedDate$
     *
     * @param text
     * @return
     */
    public static InterpolateEntity parseInterpolate(String text) {
        if (StringUtils.isBlank(text)) {
            return InterpolateEntity.TEXT;
        }

        if (text.startsWith(Constants.EXPRESS_SEP) && text.endsWith(Constants.EXPRESS_SEP)) {
            String[] fields = text.substring(1, text.length() - 1).split("\\" + Constants.EXPRESS_SEP);
            String type = fields[0];
            InterpolateType interpolateType = InterpolateType.valueOfType(type);
            InterpolateEntity entity = new InterpolateEntity();
            entity.setType(interpolateType);
            entity.setFormat(fields.length > 1 ? fields[1] : StringUtils.EMPTY);
            entity.setParamKeys(fields.length > 2 ? Arrays.asList(fields[2].split(",")) : Lists.newArrayList());
            return entity;
        }

        return InterpolateEntity.TEXT;
    }

    private static int parseInt(String valStr, int defaultVal) {
        if (StringUtils.isBlank(valStr)) {
            return defaultVal;
        }

        try {
            return Integer.parseInt(valStr);
        } catch (Exception e) {
            log.error(e.getMessage());
            return defaultVal;
        }
    }

    private static Integer parseCssAttribute(Element element, CssAttribute cssAttribute, Integer defaultVal) {
        String inlineStyle = element.attr("style");
        StringTokenizer st = new StringTokenizer(inlineStyle, ";,");
        while (st.hasMoreTokens()) {
            String property = st.nextToken();
            String[] fields = property.split(":");
            if (fields.length >= 2 && property.contains(cssAttribute.getCssName())) {
                String val = fields[1];
                return Integer.parseInt(StringUtils.trim(val)
                        .replace("px", "")
                        .replace("%", ""));
            }
        }
        return defaultVal;
    }

    private static String parseCssAttribute(Element element, CssAttribute cssAttribute, String defaultVal) {
        String inlineStyle = element.attr("style");
        StringTokenizer st = new StringTokenizer(inlineStyle, ";,");
        while (st.hasMoreTokens()) {
            String property = st.nextToken();
            String[] fields = property.split(":");
            if (fields.length >= 2 && property.contains(cssAttribute.getCssName())) {
                return StringUtils.trim(fields[1]);
            }
        }
        return defaultVal;
    }
}
