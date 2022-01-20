package com.caveup.barcode.constants;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author xw80329
 */

public enum InterpolateType {

    /**
     * 不需要进行插值计算
     */
    TEXT_CODE("text"),

    /**
     * MACHINE_CODE
     */
    MACHINE_CODE("machine_code"),

    /**
     *
     */
    PRODUCT_CODE("product_code"),

    /**
     *
     */
    QR_CODE("qr_code"),

    /**
     * 容量、箱子容量
     */
    CAPACITY("capacity"),

    /**
     * date
     */
    DATE("selectedDate");

    @Getter
    private String field;

    InterpolateType(String field) {
        this.field = field;
    }

    public static InterpolateType valueOfType(String text) {
        if (StringUtils.isBlank(text)) {
            return TEXT_CODE;
        }

        for (InterpolateType type : InterpolateType.values()) {
            if (type.name().equals("$" + text.toUpperCase() + "$")) {
                return type;
            }
        }

        return TEXT_CODE;
    }
}
