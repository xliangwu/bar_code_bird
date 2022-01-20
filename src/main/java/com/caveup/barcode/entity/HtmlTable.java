package com.caveup.barcode.entity;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * @author xw80329
 */
@ToString
@Getter
public class HtmlTable {

    private List<TableRow> rows;
    private Integer percentWidth;
    private Integer paddingTop;
    private Integer paddingBottom;
    private Integer paddingLeft;
    private Integer paddingRight;

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
