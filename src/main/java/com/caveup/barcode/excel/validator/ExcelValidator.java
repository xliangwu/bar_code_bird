package com.caveup.barcode.excel.validator;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;


/**
 * @author xw80329
 * @Date 2021/1/22
 */
public interface ExcelValidator<T> {

    /**
     * 验证excel data
     *
     * @param excelFile
     * @return
     */
    Pair<Boolean, String> validate(File excelFile);

    /**
     * @return
     */
    Class<T> getClazz();


    /**
     * 判断是否字符串过长
     *
     * @param content
     * @param maxLength
     * @return
     */
    default boolean tooLong(String content, int maxLength) {
        if (StringUtils.isBlank(content)) {
            return false;
        }

        if (content.length() >= maxLength) {
            return true;
        }
        return false;
    }
}
