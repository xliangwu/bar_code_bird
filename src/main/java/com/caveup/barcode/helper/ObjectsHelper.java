package com.caveup.barcode.helper;

/**
 * @author xw80329
 */
public class ObjectsHelper {

    /**
     * NVL expression
     *
     * @param value
     * @param defaultVal
     * @param <T>
     * @return
     */
    public static <T> T nvl(T value, T defaultVal) {
        if (null == value) {
            return defaultVal;
        }
        return value;
    }
}
