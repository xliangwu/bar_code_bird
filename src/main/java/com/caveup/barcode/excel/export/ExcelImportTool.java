package com.caveup.barcode.excel.export;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.caveup.barcode.excel.convert.TimestampStringConverter;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

/**
 * 导出数据导Excel
 *
 * @author xw80329
 * @Date 2019/11/30
 */
@Slf4j
public class ExcelImportTool {

    /**
     * 从上传文件中读取数据
     *
     * @throws Exception
     */
    public static <T> void importFromWebFile(InputStream inputStream, Class<T> dataClz, ReadListener<T> readListener, int sheet) throws Exception {
        ExcelReader excelReader = EasyExcel.read(inputStream, dataClz, readListener).registerConverter(new TimestampStringConverter()).build();
        ReadSheet readSheet = EasyExcel.readSheet(sheet).build();
        excelReader.read(readSheet);
        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
        excelReader.finish();
    }

    /**
     * 从上传文件中读取数据
     *
     * @throws Exception
     */
    public static <T> void importFromWebFile(InputStream inputStream, Class<T> dataClz, ReadListener<T> readListener, String sheetName) throws Exception {
        ExcelReader excelReader = EasyExcel.read(inputStream, dataClz, readListener).registerConverter(new TimestampStringConverter()).build();
        ReadSheet readSheet = EasyExcel.readSheet(sheetName).build();
        excelReader.read(readSheet);
        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
        excelReader.finish();
    }
}
