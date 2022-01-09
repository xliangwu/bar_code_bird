package com.caveup.barcode.helper;


/**
 * @author xw80329
 * @Date 2021/3/7
 */
public final class IpHelper {

    /**
     * 转化in_addr.s_addr 为IP地址
     * 注:1696508096 <==> "192.168.30.101"
     *
     * @param ipaddr
     * @return
     */
    public static String convertToIpAddress(long ipaddr) {
        long ip4 = ipaddr >> 24;
        long ip3 = (ipaddr & 0x00FF0000) >> 16;
        long ip2 = (ipaddr & 0x00FF0000) >> 8;
        long ip1 = ipaddr & 0x000000FF;
        return ip1 + "." + ip2 + "." + ip3 + "." + ip4;
    }
}
