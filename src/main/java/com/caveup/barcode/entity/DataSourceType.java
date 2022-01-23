package com.caveup.barcode.entity;

import lombok.Getter;

/**
 * @author xw80329
 */

public enum DataSourceType {

    /**
     * 客户产品数据类型
     */
    CUSTOMER_PRODUCT("s1");

    @Getter
    private String code;

    DataSourceType(String code) {
        this.code = code;
    }

    public static DataSourceType valueOfCode(String code) {
        for (DataSourceType sourceType : DataSourceType.values()) {
            if (sourceType.getCode().equals(code)) {
                return sourceType;
            }
        }

        return null;
    }
}
