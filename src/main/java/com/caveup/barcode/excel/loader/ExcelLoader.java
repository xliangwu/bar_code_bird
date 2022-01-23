package com.caveup.barcode.excel.loader;

import java.io.File;
import java.util.function.Function;

/**
 * @author xw80329
 * @Date 2021/1/22
 */
public interface ExcelLoader<T> {

    /**
     * load excel data
     *
     * @param excelFile
     * @return
     */
    void load(File excelFile, Function<T, T> enhance);

    /**
     * @return
     */
    Class<T> getClazz();
}
