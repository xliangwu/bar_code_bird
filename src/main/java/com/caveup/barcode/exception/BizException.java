package com.caveup.barcode.exception;

/**
 * @author xw80329
 * @date 2021-09-13
 */
public class BizException extends RuntimeException {

    private static final long serialVersionUID = -7864604160297181941L;

    /**
     * @Description : 指定具体业务错误的信息
     * @Param : message
     */
    public BizException(final String message) {
        super(message);
    }
}
