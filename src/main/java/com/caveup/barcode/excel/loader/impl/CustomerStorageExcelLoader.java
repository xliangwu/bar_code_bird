package com.caveup.barcode.excel.loader.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.caveup.barcode.excel.convert.TimestampStringConverter;
import com.caveup.barcode.excel.listener.CustomReadListener;
import com.caveup.barcode.excel.listener.impl.CustomReadListenerImpl;
import com.caveup.barcode.excel.loader.ExcelLoader;
import com.caveup.barcode.model.CustomerStorageEntity;
import com.caveup.barcode.service.impl.CustomerStorageRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author xw80329
 * @Date 2021/1/22
 */
@Slf4j
@Component
public class CustomerStorageExcelLoader implements ExcelLoader<CustomerStorageEntity> {

    @Resource
    private CustomerStorageRepositoryImpl customerStorageRepository;


    /**
     * 加载用户上传的文件，返回错误信息
     *
     * @param excelFile
     * @return
     */
    @Override
    public void load(File excelFile, Function<CustomerStorageEntity, CustomerStorageEntity> enhance) {
        Assert.isTrue(excelFile.exists(), String.format("Excel file :%s should be exist", excelFile.getAbsolutePath()));
        CustomReadListener<CustomerStorageEntity> listener = new CustomReadListenerImpl<>();
        ExcelReader excelReader = null;
        try (FileInputStream inputStream = new FileInputStream(excelFile)) {
            excelReader = EasyExcel.read(inputStream, getClazz(), listener)
                    .ignoreEmptyRow(true)
                    .headRowNumber(1)
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
        if (CollectionUtils.isNotEmpty(data)) {
            List<CustomerStorageEntity> newData = data.stream().filter(e -> StringUtils.isNoneBlank(e.getCol1())).map(enhance).collect(Collectors.toList());
            assert customerStorageRepository != null;
            Integer customerId = newData.get(0).getCustomerId();
            boolean ret = customerStorageRepository.remove(Wrappers.<CustomerStorageEntity>query().lambda().eq(CustomerStorageEntity::getCustomerId, customerId));
            log.info("delete status:{} by customer id:{}", ret, customerId);
            ret = customerStorageRepository.saveBatch(newData);
            log.info("insert status:{},count:{}", ret, newData.size());
        }
    }

    @Override
    public Class<CustomerStorageEntity> getClazz() {
        return CustomerStorageEntity.class;
    }
}
