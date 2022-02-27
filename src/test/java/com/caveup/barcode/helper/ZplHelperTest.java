package com.caveup.barcode.helper;


import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class ZplHelperTest {

    @Test
    public void sTest() {
        System.out.println("^FT273,403^A0N,27,33^FH\\^CI28^FD14121812^FS^CI27".replace("^CI27", "^CI28"));
    }

    @Test
    public void interpolateTest() throws IOException {
        File templateHtml = new ClassPathResource("data/zpl_0_0.prn").getFile();
        String text = FileUtils.readFileToString(templateHtml, "utf-8");

        Map<String, Object> params = Maps.newHashMap();
        params.put("selectedDate", new Date());
        params.put("machineCode", "1000010");
        params.put("productCode", "1015-01");
        params.put("productName", "21好奇T7治愈之柔 NB13P 内袋 1015-01 60u");
        params.put("serialNumber", "20210122002");
        params.put("sapCode", "14121812");
        params.put("capacity", "3449枚/卷");
        params.put("specification", "(260*160*50)");
        params.put("companyName", "上海福助工业有限公司");

        Optional<List<String>> res = ZplHelper.generateZplCommand(text, params, 1, 2);
        Assert.assertTrue(res.isPresent());
        res.ifPresent(e -> e.forEach(item -> log.info(item)));
    }

    /**
     * @throws IOException
     * @link http://labelary.com/service.html#java
     */
    @Test
    public void labelaryTest() throws IOException {
        File templateHtml = new ClassPathResource("data/zpl_0_0.prn").getFile();
        String text = FileUtils.readFileToString(templateHtml, "utf-8");
        String imgBase64 = ZplHelper.downloadLabelary(text);
        Assert.assertNotNull(imgBase64);
    }
}