package com.caveup.barcode.helper;

import com.caveup.barcode.constants.InterpolateType;
import com.caveup.barcode.entity.HtmlTable;
import com.caveup.barcode.entity.TableCell;
import com.caveup.barcode.entity.TableRow;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.CollectionUtils;

import java.util.Optional;

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
                cell.setInterpolateType(InterpolateType.valueOfType(text));
                row.addCell(cell);
            }
            htmlTable.addRow(row);
        }
        return Optional.of(htmlTable);
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
}
