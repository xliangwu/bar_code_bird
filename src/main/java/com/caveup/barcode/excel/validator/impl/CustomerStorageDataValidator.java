package com.caveup.barcode.excel.validator.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.caveup.barcode.excel.convert.TimestampStringConverter;
import com.caveup.barcode.excel.listener.CustomReadListener;
import com.caveup.barcode.excel.listener.impl.CustomReadListenerImpl;
import com.caveup.barcode.excel.validator.ExcelValidator;
import com.caveup.barcode.model.CustomerStorageEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;

@Slf4j
public class CustomerStorageDataValidator implements ExcelValidator<CustomerStorageEntity> {

    /**
     * 验证用户上传的文件，返回错误信息
     *
     * @param excelFile
     * @return
     */
    @Override
    public Pair<Boolean, String> validate(File excelFile) {
        Assert.isTrue(excelFile.exists(), String.format("Excel file :%s should be exist", excelFile.getAbsolutePath()));
        CustomReadListener<CustomerStorageEntity> listener = new CustomReadListenerImpl<>();
        ExcelReader excelReader = null;
        try (FileInputStream inputStream = new FileInputStream(excelFile)) {
            excelReader = EasyExcel.read(inputStream, getClazz(), listener)
                    .ignoreEmptyRow(true)
                    .headRowNumber(3)
                    .registerConverter(new TimestampStringConverter())
                    .build();
            ReadSheet readSheet = EasyExcel.readSheet(0).build();
            excelReader.read(readSheet);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
            if (null != excelReader) {
                excelReader.finish();
            }
        }

        Collection<CustomerStorageEntity> data = listener.getData();
        StringBuilder msg = new StringBuilder();
        boolean pass = true;

        if (CollectionUtils.isEmpty(data)) {
            return Pair.of(false, "您上传的excel文件为空，请重新上传");
        }
        return Pair.of(pass, msg.toString());
    }

    @Override
    public Class<CustomerStorageEntity> getClazz() {
        return CustomerStorageEntity.class;
    }
}
