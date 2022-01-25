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

    TEXT_ALIGN("text-align"),
    VERTICAL_ALIGN("vertical-align"),
    FONT_SIZE("font-size"),
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
