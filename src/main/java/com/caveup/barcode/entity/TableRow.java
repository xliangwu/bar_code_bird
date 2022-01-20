package com.caveup.barcode.entity;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * @author xw80329
 */
@Getter
@ToString
public class TableRow {

    private List<TableCell> cells;

    public TableRow(List<TableCell> cells) {
        this.cells = cells;
    }

    public TableRow() {
        this(Lists.newArrayList());
    }

    public void addCell(TableCell cell) {
        if (null == cells) {
            cells = Lists.newArrayList();
        }
        cells.add(cell);
    }

    public int calculateCols() {
        return cells.stream().mapToInt(TableCell::getColSpan).reduce(Integer::sum).getAsInt();
    }
}
