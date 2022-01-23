package com.caveup.barcode.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.caveup.barcode.entity.Entity;
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

    @ExcelProperty("智审模型编码")
    @TableField("col_1")
    private String col1;

    @ExcelProperty("智审模型名称")
    @TableField("col_2")
    private String col2;

    @TableField("col_3")
    private String col3;

    @TableField("col_4")
    private String col4;

    @TableField("col_5")
    private String col5;

    @TableField("col_6")
    private String col6;

    @TableField("col_7")
    private String col7;

    @TableField("col_8")
    private String col8;

    @TableField("col_9")
    private String col9;

    private Date createdTime;

    @Override
    public Integer getPrimaryKey() {
        return this.id;
    }
}
