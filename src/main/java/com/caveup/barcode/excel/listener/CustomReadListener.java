package com.caveup.barcode.excel.listener;

import com.alibaba.excel.read.listener.ReadListener;

import java.util.Collection;

/**
 * @author xw80329
 * @Date 2020/12/26
 */
public interface CustomReadListener<T> extends ReadListener<T> {

    /**
     * 返回所有的数据集合
     *
     * @return
     */
    Collection<T> getData();
}
