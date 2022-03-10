package com.caveup.barcode.constants;

import lombok.Getter;

/**
 * @author xw80329
 */

public enum PrintType {

    /**
     * 2 * 2
     */
    P2_2(12, 6, 0, 3),


    /**
     * 2 *3
     */
    P2_3(9, 2, 0, 5);

    @Getter
    private int fontSize;

    @Getter
    private int outTablePaddingTop;

    @Getter
    private int qrCodePadding;

    @Getter
    private int pageBottomIndex;

    private PrintType(int fontSize, int outTablePaddingTop, int qrCodePadding, int pageBottomIndex) {
        this.fontSize = fontSize;
        this.outTablePaddingTop = outTablePaddingTop;
        this.qrCodePadding = qrCodePadding;
        this.pageBottomIndex = pageBottomIndex;
    }
}
