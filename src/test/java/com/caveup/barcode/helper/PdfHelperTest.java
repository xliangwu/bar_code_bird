package com.caveup.barcode.helper;


import com.caveup.barcode.constants.PrintType;
import com.caveup.barcode.entity.HtmlTable;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class PdfHelperTest {

    @Test
    public void aTest() {
        String a = "1015-01\n21好奇T7治愈之柔 NB13P 内袋 1015-" +
                "01 60u";
        System.out.println(a.split("\n"));
    }

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
        Map<String, Object> params = Maps.newHashMap();
        Optional<String> outputOption = PdfHelper.generatePrintPdf(optional.get(), params, 0, 11, PrintType.P2_2);
        Assert.assertTrue(outputOption.isPresent());
        log.info(outputOption.get());
    }

    @Test
    public void generate2PrintPdf() throws IOException {
        File templateHtml = new ClassPathResource("data/template.html").getFile();
        String text = FileUtils.readFileToString(templateHtml, "utf-8");

        Optional<HtmlTable> optional = JsoupHelper.parseTable(text);
        Assert.assertTrue(optional.isPresent());
        Map<String, Object> params = Maps.newHashMap();
        params.put("selectedDate", new Date());
        params.put("machineCode", "1000010");
        params.put("productCode", "8888888");
        params.put("serialNumber", "20210122002");
        params.put("capacity", 1000);
        Optional<String> outputOption = PdfHelper.generatePrintPdf(optional.get(), params, 0, 11, PrintType.P2_3);
        Assert.assertTrue(outputOption.isPresent());
        log.info(outputOption.get());
    }

    @Test
    public void generate3PrintPdf() throws IOException {
        File templateHtml = new ClassPathResource("data/template1.html").getFile();
        String text = FileUtils.readFileToString(templateHtml, "utf-8");

        Optional<HtmlTable> optional = JsoupHelper.parseTable(text);
        Assert.assertTrue(optional.isPresent());
        Map<String, Object> params = Maps.newHashMap();
        params.put("selectedDate", new Date());
        params.put("machineCode", "1000010");
        params.put("productCode", "1015-01\n21好奇T7治愈之柔 NB13P 内袋 1015-01 60u");
        params.put("serialNumber", "20210122002");
        params.put("sapCode", "14121812");
        params.put("capacity", "3449枚/卷");
        params.put("specification", "(260*160*50)");
        Optional<String> outputOption = PdfHelper.generatePrintPdf(optional.get(), params, 0, 1000, PrintType.P2_2);
        Assert.assertTrue(outputOption.isPresent());
        log.info(outputOption.get());
    }

    @Test
    public void splitTest() {
        String a = "1015-01\n21好奇T7治愈之柔 NB13P 内袋 1015-01 60u";
        log.info("{}=>{}", a.split("\n"));
    }
}