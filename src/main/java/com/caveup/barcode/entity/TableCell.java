package com.caveup.barcode.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author xw80329
 */
@Getter
@Setter
@ToString
public class TableCell {

    private int colSpan;
    private int rowSpan;
    private String text;
    private boolean bold;
    private int fontSize;
    private InterpolateEntity interpolate;
    private CssTextAlignment alignment;
    private CssVerticalAlignment verticalAlignment;
    private Integer paddingTop;
    private Integer paddingBottom;
    private Integer paddingLeft;
    private Integer paddingRight;
    private int width;
    private String fontWeight;

    public TableCell(String text, int colSpan, int rowSpan) {
        this.text = text;
        this.colSpan = colSpan;
        this.rowSpan = rowSpan;
        this.interpolate = InterpolateEntity.TEXT;
    }

    public TableCell(String text) {
        this(text, 1, 1);
    }
}
