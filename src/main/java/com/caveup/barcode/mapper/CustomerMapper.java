package com.caveup.barcode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.caveup.barcode.model.CustomerEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author xw80329
 * @since 2022-01-09
 */
@Mapper
public interface CustomerMapper extends BaseMapper<CustomerEntity> {

}
