package com.caveup.barcode.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.caveup.barcode.exception.BizException;
import com.caveup.barcode.mapper.CustomerStorageMapper;
import com.caveup.barcode.model.CustomerStorageEntity;
import com.caveup.barcode.service.CustomerStorageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xw80329
 * @since 2022-01-09
 */
@Slf4j
@Service
public class CustomerStorageRepositoryImpl extends ServiceImpl<CustomerStorageMapper, CustomerStorageEntity> implements CustomerStorageRepository {

    @Override
    public Page<CustomerStorageEntity> listByPage(int page, int pageSize) {
        log.info("query customerStorageEntity: page = {} pageSize = {}", page, pageSize);
        Page<CustomerStorageEntity> result = super.page(new Page<>(page, pageSize));
        log.info("query result [customerStorageEntity] done,size:{} ", result.getRecords().size());
        return result;
    }

    @Override
    public CustomerStorageEntity getById(int id) {
        log.info("query customerStorageEntity id:{}", id);
        CustomerStorageEntity customerStorageEntity = super.getById(id);
        return customerStorageEntity;
    }

    @Override
    public int insert(CustomerStorageEntity customerStorageEntity) {
        if (super.save(customerStorageEntity)) {
            log.info("insert customerStorageEntity done,id:{}", customerStorageEntity.getPrimaryKey());
            return customerStorageEntity.getPrimaryKey();
        } else {
            log.error("insert customerStorageEntity failed");
            throw new BizException("添加失败");
        }
    }

    @Override
    public int deleteById(int id) {
        log.info("delete record,id:{}", id);
        if (super.removeById(id)) {
            log.info("delete id:{} done", id);
            return id;
        } else {
            log.error("delete id:{} customerStorageEntity failed", id);
            throw new BizException("delete failed[id=" + id + "]");
        }
    }

    @Override
    public int update(CustomerStorageEntity customerStorageEntity) {
        log.info("update id:{}", customerStorageEntity.getPrimaryKey());
        if (super.updateById(customerStorageEntity)) {
            log.info("update id:{} done", customerStorageEntity.getPrimaryKey());
            return customerStorageEntity.getPrimaryKey();
        } else {
            log.error("update id:{} failed", customerStorageEntity.getPrimaryKey());
            throw new BizException("update failed [id=" + customerStorageEntity.getPrimaryKey() + "]");
        }
    }

}
