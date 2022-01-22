package com.caveup.barcode.helper;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

@Slf4j
public class QrCodeHelperTest {

    @Test
    public void createQrCode() {
        Optional<String> output = QrCodeHelper.createQrCode("aaa", 100, 100);
        Assert.assertTrue(output.isPresent());
        log.info(output.get());
    }
}