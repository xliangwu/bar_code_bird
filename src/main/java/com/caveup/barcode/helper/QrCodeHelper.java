package com.caveup.barcode.helper;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author xw80329
 */
@Slf4j
public class QrCodeHelper {

    private static final String QRCODE_TYPE = "jpg";

    /**
     * QR_CODE generate
     *
     * @param content
     * @param width
     * @param height
     * @return
     */
    public static Optional<String> createQrCode(String content, int width, int height) {
        try {
            File output = File.createTempFile("qr_", ".jpg");
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            hints.put(EncodeHintType.MARGIN, 1);
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            MatrixToImageWriter.writeToPath(bitMatrix, QRCODE_TYPE, output.toPath());
            return Optional.of(output.getAbsolutePath());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    /**
     * @param content
     * @param width
     * @param height
     * @return
     * @throws IOException
     */
    public static byte[] createQrCodeData(String content, int width, int height) throws IOException {
        Optional<String> output = createQrCode(content, width, height);
        if (output.isPresent()) {
            File file = new File(output.get());
            byte[] contents = FileUtils.readFileToByteArray(file);
            log.info("qr code image:{}", file.getAbsolutePath());
            FileUtils.deleteQuietly(file);
            return contents;
        }
        return null;
    }
}
