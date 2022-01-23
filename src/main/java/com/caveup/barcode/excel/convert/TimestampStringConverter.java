package com.caveup.barcode.excel.convert;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.DateUtils;

import java.sql.Timestamp;

/**
 * 注册Timestamp 转换器
 *
 * @author xw80329
 * @Date 2019/11/30
 */
public class TimestampStringConverter implements Converter<Timestamp> {
    /**
     * Back to object types in Java
     *
     * @return Support for Java class
     */
    @Override
    public Class supportJavaTypeKey() {
        return Timestamp.class;
    }

    /**
     * Back to object enum in excel
     *
     * @return Support for {@link CellDataTypeEnum}
     */
    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    /**
     * Convert excel objects to Java objects
     *
     * @param cellData            Excel cell data.NotNull.
     * @param contentProperty     Content property.Nullable.
     * @param globalConfiguration Global configuration.NotNull.
     * @return Data to put into a Java object
     * @throws Exception Exception.
     */
    @Override
    public Timestamp convertToJavaData(ReadCellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
            return new Timestamp(DateUtils.parseDate(cellData.getStringValue(), null).getTime());
        } else {
            return new Timestamp(DateUtils.parseDate(cellData.getStringValue(),
                    contentProperty.getDateTimeFormatProperty().getFormat()).getTime());
        }
    }

    /**
     * Convert Java objects to excel objects
     *
     * @param value               Java Data.NotNull.
     * @param contentProperty     Content property.Nullable.
     * @param globalConfiguration Global configuration.NotNull.
     * @return Data to put into a Excel
     * @throws Exception Exception.
     */
    @Override
    public WriteCellData<?> convertToExcelData(Timestamp value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
            return new WriteCellData(DateUtils.format(value, null));
        } else {
            return new WriteCellData(DateUtils.format(value, contentProperty.getDateTimeFormatProperty().getFormat()));
        }
    }
}
