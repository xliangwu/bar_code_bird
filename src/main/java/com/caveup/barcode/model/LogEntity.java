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
@TableName("bar_log")
@ApiModel(value = "LogEntity对象", description = "")
public class LogEntity extends Entity<Integer> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String loggedEvent;

    private String loggedEntity;

    private Date loggedTime;

    private Integer loggedUserId;

    @Override
    public Integer getPrimaryKey() {
        return this.id;
    }

}
