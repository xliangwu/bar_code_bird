package com.caveup.barcode.helper;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.awt.image.BufferedImage;
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
            BitMatrix matrixWithoutWhite = deleteWhite(bitMatrix);
            MatrixToImageWriter.writeToPath(matrixWithoutWhite, QRCODE_TYPE, output.toPath());
            return Optional.of(output.getAbsolutePath());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    /**
     * 强制将白边去掉
     *
     * @param matrix
     * @return 裁剪后的二维码（实际二维码的大小）
     * @explain 虽然生成二维码时，已经将margin的值设为了0，但是在实际生成二维码时有时候还是会生成白色的边框，边框的宽度为10px；
     * 白边的生成还与设定的二维码的宽、高及二维码内容的多少（内容越多，生成的二维码越密集）有关；
     * 因为是在生成二维码之后，才将白边裁掉，所以裁剪后的二维码（实际二维码的宽、高）肯定不是你想要的尺寸，只能自己一点点试喽！
     */
    private static BitMatrix deleteWhite(BitMatrix matrix) {
        int[] rec = matrix.getEnclosingRectangle();
        int resWidth = rec[2] + 1;
        int resHeight = rec[3] + 1;

        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (matrix.get(i + rec[0], j + rec[1])) {
                    resMatrix.set(i, j);
                }
            }
        }

        int width = resMatrix.getWidth();
        int height = resMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, resMatrix.get(x, y) ? 0 : 255);
            }
        }

        return resMatrix;
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
