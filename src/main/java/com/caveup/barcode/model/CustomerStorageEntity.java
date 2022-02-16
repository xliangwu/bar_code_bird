package com.caveup.barcode.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.caveup.barcode.entity.Entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author xw80329
 * @since 2022-01-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("bar_customer_storage")
@ApiModel(value = "CustomerStorageEntity对象", description = "")
public class CustomerStorageEntity extends Entity<Integer> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer customerId;

    @ExcelProperty("接单卡号")
    @TableField("col_1")
    private String col1;

    @ExcelProperty("SAP代码")
    @TableField("col_2")
    private String col2;

    @ExcelProperty("品名码")
    @TableField("col_3")
    private String col3;

    @ExcelProperty("产品名")
    @TableField("col_4")
    private String col4;

    @ExcelProperty("规格")
    @TableField("col_5")
    private String col5;

    @ExcelProperty("数量")
    @TableField("col_6")
    private String col6;

    @ExcelProperty("纸箱编号")
    @TableField("col_7")
    private String col7;

    @TableField("col_8")
    private String col8;

    @TableField("col_9")
    private String col9;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdTime;

    @Override
    public Integer getPrimaryKey() {
        return this.id;
    }
}
