package com.caveup.barcode.result.helper;


import com.caveup.barcode.result.ApiStatusCode;
import com.caveup.barcode.result.model.ApiResultModel;

/**
 * 接口结果返回封装帮助工具类
 *
 * <p>
 * 该工具类操作的实体类是：{@link ApiResultModel}
 * </p>
 *
 * <p>
 * 我们提供了如下三个方法：
 * </p>
 *
 * <ul>
 * <li>success(S code, String message, T data)：成功，并返回的数据的方法</li>
 * <li>success(S code, String message)：成功，不返回数据</li>
 * <li>error(S code, String message)：失败</li>
 * </ul>
 *
 * @author xw80329
 * @since 1.0
 */
public class ApiResultHelper {

    /**
     * 成功，并返回数据
     *
     * @param message 描述信息
     * @param data    数据
     * @param <T>     数据类型
     * @return 返回结果封装 {@link ApiResultModel}
     */
    public static <T> ApiResultModel<T> success(String message, T data) {
        return new ApiResultModel(ApiStatusCode.SUCCESS.getCode(), message, data);
    }

    public static <T> ApiResultModel<T> success(T data) {
        return new ApiResultModel(ApiStatusCode.SUCCESS.getCode(), ApiStatusCode.SUCCESS.getComments(), data);
    }


    /**
     * 成功，无数据返回
     *
     * @param code    返回码
     * @param message 描述信息
     * @param <T>     数据类型
     * @return 返回结果封装 {@link ApiResultModel}
     */
    public static <T> ApiResultModel<T> success(Integer code, String message) {
        return new ApiResultModel<>(code, message, null);
    }

    /**
     * 成功，无数据返回
     *
     * @param <T> 数据类型
     * @return 返回结果封装 {@link ApiResultModel}
     */
    public static <T> ApiResultModel<T> success() {
        return new ApiResultModel<>(ApiStatusCode.SUCCESS.getCode(), ApiStatusCode.SUCCESS.getComments(), null);
    }

    /**
     * 失败，无数据返回
     *
     * @param message 描述信息
     * @param <T>     数据类型
     * @return 返回结果封装 {@link ApiResultModel}
     */
    public static <T> ApiResultModel<T> error(Integer code, String message) {
        return new ApiResultModel<>(code, message);
    }

    /**
     * 失败，无数据返回
     *
     * @param code 描述信息
     * @param <T>  数据类型
     * @return 返回结果封装 {@link ApiResultModel}
     */
    public static <T> ApiResultModel<T> error(ApiStatusCode code) {
        return new ApiResultModel<>(code.getCode(), code.getComments());
    }

}
