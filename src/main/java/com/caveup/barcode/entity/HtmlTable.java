package com.caveup.barcode.entity;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author xw80329
 */
@ToString
@Getter
public class HtmlTable {

    private List<TableRow> rows;

    @Setter
    private Integer percentWidth;

    @Setter
    private Integer paddingTop;

    @Setter
    private Integer paddingBottom;

    @Setter
    private Integer paddingLeft;

    @Setter
    private Integer paddingRight;

    @Setter
    private Integer fontSize;

    @Setter
    private Integer f1FontSize;

    @Setter
    private Integer f2FontSize;

    public HtmlTable() {
        this.rows = Lists.newArrayList();
    }

    public HtmlTable(List<TableRow> rows) {
        this.rows = rows;
    }

    public void addRow(TableRow row) {
        if (null == rows) {
            rows = Lists.newArrayList();
        }

        rows.add(row);
    }

}
