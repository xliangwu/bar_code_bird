package com.caveup.barcode.constants;

import lombok.Getter;

/**
 * @author xw80329
 */
public enum CssAttribute {

    /**
     *
     */
    WIDTH("width"),

    HEIGHT("height"),
    TEXT_ALIGN("text-align"),
    VERTICAL_ALIGN("vertical-align"),
    FONT_SIZE("font-size"),
    PARAGRAPH_HEADER_FONT_SIZE("header-font-size"),
    F1_HEADER_FONT_SIZE("f1-font-size"),
    F2_HEADER_FONT_SIZE("f2-font-size"),
    FONT_WEIGHT("font-weight"),
    /**
     * css padding
     */
    PADDING_LEFT("padding-left"),
    PADDING_RIGHT("padding-right"),
    PADDING_TOP("padding-top"),
    PADDING_BOTTOM("padding-bottom");

    @Getter
    private String cssName;

    CssAttribute(String cssName) {
        this.cssName = cssName;
    }
}
