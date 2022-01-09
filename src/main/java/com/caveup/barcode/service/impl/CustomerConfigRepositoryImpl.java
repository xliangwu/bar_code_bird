package com.caveup.barcode.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.caveup.barcode.exception.BizException;
import com.caveup.barcode.mapper.CustomerConfigMapper;
import com.caveup.barcode.model.CustomerConfigEntity;
import com.caveup.barcode.service.CustomerConfigRepository;
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
public class CustomerConfigRepositoryImpl extends ServiceImpl<CustomerConfigMapper, CustomerConfigEntity> implements CustomerConfigRepository {

    @Override
    public Page<CustomerConfigEntity> listByPage(int page, int pageSize) {
        log.info("query customerConfigEntity: page = {} pageSize = {}", page, pageSize);
        Page<CustomerConfigEntity> result = super.page(new Page<>(page, pageSize));
        log.info("query result [customerConfigEntity] done,size:{} ", result.getRecords().size());
        return result;
    }

    @Override
    public CustomerConfigEntity getById(int id) {
        log.info("query customerConfigEntity id:{}", id);
        CustomerConfigEntity customerConfigEntity = super.getById(id);
        return customerConfigEntity;
    }

    @Override
    public int insert(CustomerConfigEntity customerConfigEntity) {
        if (super.save(customerConfigEntity)) {
            log.info("insert customerConfigEntity done,id:{}", customerConfigEntity.getPrimaryKey());
            return customerConfigEntity.getPrimaryKey();
        } else {
            log.error("insert customerConfigEntity failed");
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
            log.error("delete id:{} customerConfigEntity failed", id);
            throw new BizException("delete failed[id=" + id + "]");
        }
    }

    @Override
    public int update(CustomerConfigEntity customerConfigEntity) {
        log.info("update id:{}", customerConfigEntity.getPrimaryKey());
        if (super.updateById(customerConfigEntity)) {
            log.info("update id:{} done", customerConfigEntity.getPrimaryKey());
            return customerConfigEntity.getPrimaryKey();
        } else {
            log.error("update id:{} failed", customerConfigEntity.getPrimaryKey());
            throw new BizException("update failed [id=" + customerConfigEntity.getPrimaryKey() + "]");
        }
    }

}
