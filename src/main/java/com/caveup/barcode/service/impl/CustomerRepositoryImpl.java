package com.caveup.barcode.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.caveup.barcode.exception.BizException;
import com.caveup.barcode.mapper.CustomerMapper;
import com.caveup.barcode.model.CustomerEntity;
import com.caveup.barcode.service.CustomerRepository;
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
public class CustomerRepositoryImpl extends ServiceImpl<CustomerMapper, CustomerEntity> implements CustomerRepository {

    @Override
    public Page<CustomerEntity> listByPage(int page, int pageSize) {
        log.info("query customerEntity: page = {} pageSize = {}", page, pageSize);
        Page<CustomerEntity> result = super.page(new Page<>(page, pageSize));
        log.info("query result [customerEntity] done,size:{} ", result.getRecords().size());
        return result;
    }

    @Override
    public CustomerEntity getById(int id) {
        log.info("query customerEntity id:{}", id);
        CustomerEntity customerEntity = super.getById(id);
        return customerEntity;
    }

    @Override
    public int insert(CustomerEntity customerEntity) {
        if (super.save(customerEntity)) {
            log.info("insert customerEntity done,id:{}", customerEntity.getPrimaryKey());
            return customerEntity.getPrimaryKey();
        } else {
            log.error("insert customerEntity failed");
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
            log.error("delete id:{} customerEntity failed", id);
            throw new BizException("delete failed[id=" + id + "]");
        }
    }

    @Override
    public int update(CustomerEntity customerEntity) {
        log.info("update id:{}", customerEntity.getPrimaryKey());
        if (super.updateById(customerEntity)) {
            log.info("update id:{} done", customerEntity.getPrimaryKey());
            return customerEntity.getPrimaryKey();
        } else {
            log.error("update id:{} failed", customerEntity.getPrimaryKey());
            throw new BizException("update failed [id=" + customerEntity.getPrimaryKey() + "]");
        }
    }

}
