package com.caveup.barcode.excel.listener.impl;

import com.alibaba.excel.context.AnalysisContext;
import com.caveup.barcode.excel.listener.CustomReadListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author xw80329
 * @Date 2020/12/26
 */

public class CustomReadListenerImpl<T> implements CustomReadListener<T> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final List<T> allData = new ArrayList<>(1024);

    @Override
    public Collection<T> getData() {
        return allData;
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        //Empty
    }

    @Override
    public void invoke(T data, AnalysisContext context) {
        allData.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        logger.info("Total record:{}", allData.size());
    }

    @Override
    public boolean hasNext(AnalysisContext context) {
        return true;
    }
}
