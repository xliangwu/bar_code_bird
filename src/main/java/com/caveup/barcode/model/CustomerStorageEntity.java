package com.caveup.barcode.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.caveup.barcode.entity.Entity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

    private String col1;

    private String col2;

    private String col3;

    private String col4;

    private String col5;

    private String col6;

    private String col7;

    private String col8;

    private String col9;


    @Override
    public Integer getPrimaryKey() {
        return this.id;
    }
}
