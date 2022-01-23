package com.caveup.barcode.helper;

import com.alibaba.excel.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Content 类型
 *
 * @author xw80329
 */
public class ContentTypeUtil {

    /**
     * 返回Content Type类型
     *
     * @param name
     * @param charset
     * @return
     */
    public static String getContentTypeBySuffix(String name, Boolean charset) {
        String contentType = StringUtils.EMPTY;
        if (name.contains(".xls")) {
            contentType = "application/vnd.ms-excel";
        }
        if (name.contains(".pdf")) {
            contentType = "application/pdf";
        }
        if (name.contains(".xlsx")) {
            contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        }
        if (name.contains(".doc")) {
            contentType = "application/msword";
        }
        if (name.contains(".docx")) {
            contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        }
        if (name.contains(".ppt")) {
            contentType = "application/vnd.ms-powerpoint";
        }
        if (name.contains(".pptx")) {
            contentType = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
        }

        if (name.contains(".zip")) {
            contentType = "application/zip, application/octet-stream";
        }
        if (charset) {
            return contentType + ";charset=utf-8";
        }
        return contentType;
    }

    public static String getContentDisposition(String fileName) {
        String headerValue = "attachment;";
        headerValue += " filename=\"" + encodeUriComponent(fileName) + "\";";
        headerValue += " filename*=utf-8''" + encodeUriComponent(fileName);
        return headerValue;
    }

    /**
     * <pre>
     * 符合 RFC 3986 标准的“百分号URL编码”
     * 在这个方法里，空格会被编码成%20，而不是+
     * 和浏览器的encodeURIComponent行为一致
     * </pre>
     *
     * @param value
     * @return
     */
    private static String encodeUriComponent(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

}
