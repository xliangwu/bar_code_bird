package com.caveup.barcode.entity;

import com.caveup.barcode.constants.InterpolateType;
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
    private InterpolateType interpolateType;
    private CssTextAlignment alignment;
    private Integer paddingTop;
    private Integer paddingBottom;
    private Integer paddingLeft;
    private Integer paddingRight;

    public TableCell(String text, int colSpan, int rowSpan) {
        this.text = text;
        this.colSpan = colSpan;
        this.rowSpan = rowSpan;
        this.interpolateType = InterpolateType.TEXT_CODE;
    }

    public TableCell(String text) {
        this(text, 1, 1);
    }
}
