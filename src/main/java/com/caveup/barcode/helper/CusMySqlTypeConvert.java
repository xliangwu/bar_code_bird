package com.caveup.barcode.helper;

import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;

/**
 * mybatisplus 默认日期格式是JDK8 LocalDateTime
 *
 * @author xw80329
 * @Date 2020/12/7
 */
public class CusMySqlTypeConvert extends MySqlTypeConvert {

    /**
     * @inheritDoc
     */
    @Override
    public IColumnType processTypeConvert(GlobalConfig config, String fieldType) {

        if (fieldType.toLowerCase().contains("date") || fieldType.toLowerCase().contains("time")) {
            return DbColumnType.DATE;
        }
        return super.processTypeConvert(config, fieldType);
    }
}
