package com.caveup.barcode.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.caveup.barcode.exception.BizException;
import com.caveup.barcode.mapper.SysCategoryMapper;
import com.caveup.barcode.model.SysCategoryEntity;
import com.caveup.barcode.service.SysCategoryRepository;
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
public class SysCategoryRepositoryImpl extends ServiceImpl<SysCategoryMapper, SysCategoryEntity> implements SysCategoryRepository {

    @Override
    public Page<SysCategoryEntity> listByPage(int page, int pageSize) {
        log.info("query sysCategoryEntity: page = {} pageSize = {}", page, pageSize);
        Page<SysCategoryEntity> result = super.page(new Page<>(page, pageSize));
        log.info("query result [sysCategoryEntity] done,size:{} ", result.getRecords().size());
        return result;
    }

    @Override
    public SysCategoryEntity getById(int id) {
        log.info("query sysCategoryEntity id:{}", id);
        SysCategoryEntity sysCategoryEntity = super.getById(id);
        return sysCategoryEntity;
    }

    @Override
    public int insert(SysCategoryEntity sysCategoryEntity) {
        if (super.save(sysCategoryEntity)) {
            log.info("insert sysCategoryEntity done,id:{}", sysCategoryEntity.getPrimaryKey());
            return sysCategoryEntity.getPrimaryKey();
        } else {
            log.error("insert sysCategoryEntity failed");
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
            log.error("delete id:{} sysCategoryEntity failed", id);
            throw new BizException("delete failed[id=" + id + "]");
        }
    }

    @Override
    public int update(SysCategoryEntity sysCategoryEntity) {
        log.info("update id:{}", sysCategoryEntity.getPrimaryKey());
        if (super.updateById(sysCategoryEntity)) {
            log.info("update id:{} done", sysCategoryEntity.getPrimaryKey());
            return sysCategoryEntity.getPrimaryKey();
        } else {
            log.error("update id:{} failed", sysCategoryEntity.getPrimaryKey());
            throw new BizException("update failed [id=" + sysCategoryEntity.getPrimaryKey() + "]");
        }
    }

}
