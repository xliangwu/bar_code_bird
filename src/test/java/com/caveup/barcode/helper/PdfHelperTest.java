package com.caveup.barcode.helper;


import com.caveup.barcode.constants.PrintType;
import com.caveup.barcode.entity.HtmlTable;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.Optional;

@Slf4j
public class PdfHelperTest {

    @Test
    public void generatePrintPdf() {

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
        Assert.assertTrue(optional.isPresent());
        Map<String, String> params = Maps.newHashMap();
        Optional<String> outputOption = PdfHelper.generatePrintPdf(optional.get(), params, 0, 11, PrintType.P2_2);
        Assert.assertTrue(outputOption.isPresent());
        log.info(outputOption.get());
    }
}