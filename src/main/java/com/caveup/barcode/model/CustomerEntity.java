package com.caveup.barcode.model;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("bar_customer")
@ApiModel(value = "CustomerEntity对象", description = "")
public class CustomerEntity extends Entity<Integer> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String customerName;

    private Date createdTime;

    private Integer createdUserId;

    private Date modifiedTime;

    private Integer modifiedUserId;

    @Override
    public Integer getPrimaryKey() {
        return this.id;
    }
}
