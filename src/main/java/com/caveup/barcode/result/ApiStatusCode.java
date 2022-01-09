package com.caveup.barcode.result;

import lombok.Getter;

/**
 * 2xx成功
 * 这类状态代码表明服务器成功地接受了客户端请求
 * <p>
 * 4xx客户端错误
 * 发生错误，客户端似乎有问题。例如，客户端请求不存在的页面，客户端未提供有效的身份验证信息。400错误的请求。
 * <p>
 * 5xx服务器错误
 * 服务器由于遇到错误而不能完成该请求。
 * 500内部服务器错误。
 * 500.12应用程序正忙于在Web服务器上重新启动。
 * 500.13Web服务器太忙。
 * 500.15不允许直接请求Global.asa。
 * 500.16–UNC授权凭据不正确。这个错误代码为IIS6.0所专用。
 * 500.18–URL授权存储不能打开。这个错误代码为IIS6.0所专用。
 * 500.100内部ASP错误。
 * 501页眉值指定了未实现的配置。
 * 502Web服务器用作网关或代理服务器时收到了无效响应。
 * 502.1CGI应用程序超时。
 * 502.2CGI应用程序出错。application.
 * 503服务不可用。这个错误代码为IIS6.0所专用。
 * 504网关超时。
 * 505HTTP版本不受支持。
 * <p>
 * 5xxx 代表系统自定义出错，
 * 999 代表系统未定义错误
 *
 * @author xw80329
 * @date 2019/4/28 17:11
 */
public enum ApiStatusCode {
    /**
     * 系统成功
     **/
    SUCCESS(0, "ok"),

    /**
     * 未定义错误代码或者系统异常的系统错误，隐藏错误原因（不传给前端）
     */
    UNKNOWN_ERROR(999, "系统运行出错"),

    /**
     * 参数校验
     */
    PARAM_ERROR(5000, "必选参数为空"),
    EMPTY_DATA(6000, "结果为空"),
    PARAM_EMPTY(5001, "必选参数为空");

    @Getter
    private Integer code;

    @Getter
    private String comments;

    ApiStatusCode(Integer code, String comments) {
        this.code = code;
        this.comments = comments;
    }
}
