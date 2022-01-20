package com.caveup.barcode.helper;

import com.caveup.barcode.entity.HtmlTable;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

@Slf4j
public class JsoupHelperTest {

    /**
     * @link https://www.tablesgenerator.com/html_tables
     */
    @Test
    public void parseTable() {
        String text = "<style type=\"text/css\">\n" +
                ".tg  {border-collapse:collapse;border-spacing:0;}\n" +
                ".tg td{border-color:black;border-style:solid;border-width:1px;font-family:Arial, sans-serif;font-size:14px;\n" +
                "  overflow:hidden;padding:10px 5px;word-break:normal;}\n" +
                ".tg th{border-color:black;border-style:solid;border-width:1px;font-family:Arial, sans-serif;font-size:14px;\n" +
                "  font-weight:normal;overflow:hidden;padding:10px 5px;word-break:normal;}\n" +
                ".tg .tg-0pky{border-color:inherit;text-align:left;vertical-align:top}\n" +
                "</style>\n" +
                "<table class=\"tg\">\n" +
                "<thead>\n" +
                "  <tr>\n" +
                "    <th class=\"tg-0pky\">test</th>\n" +
                "    <th class=\"tg-0pky\">test</th>\n" +
                "    <th class=\"tg-0pky\">test</th>\n" +
                "    <th class=\"tg-0pky\">test</th>\n" +
                "  </tr>\n" +
                "</thead>\n" +
                "<tbody>\n" +
                "  <tr>\n" +
                "    <td class=\"tg-0pky\"></td>\n" +
                "    <td class=\"tg-0pky\"></td>\n" +
                "    <td class=\"tg-0pky\" colspan=\"2\"></td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td class=\"tg-0pky\"></td>\n" +
                "    <td class=\"tg-0pky\" colspan=\"2\" rowspan=\"2\"></td>\n" +
                "    <td class=\"tg-0pky\"></td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td class=\"tg-0pky\"></td>\n" +
                "    <td class=\"tg-0pky\"></td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td class=\"tg-0pky\"></td>\n" +
                "    <td class=\"tg-0pky\"></td>\n" +
                "    <td class=\"tg-0pky\"></td>\n" +
                "    <td class=\"tg-0pky\"></td>\n" +
                "  </tr>\n" +
                "</tbody>\n" +
                "</table>";

        Optional<HtmlTable> optional = JsoupHelper.parseTable(text);
        Assert.assertNotNull(optional);
        Assert.assertTrue(optional.isPresent());
        log.info("table:{}", optional.get());
    }
}