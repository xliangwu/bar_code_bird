package com.caveup.barcode.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.caveup.barcode.exception.BizException;
import com.caveup.barcode.mapper.SysMachineMapper;
import com.caveup.barcode.model.SysMachineEntity;
import com.caveup.barcode.service.SysMachineRepository;
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
public class SysMachineRepositoryImpl extends ServiceImpl<SysMachineMapper, SysMachineEntity> implements SysMachineRepository {

    @Override
    public Page<SysMachineEntity> listByPage(int page, int pageSize) {
        log.info("query sysMachineEntity: page = {} pageSize = {}", page, pageSize);
        Page<SysMachineEntity> result = super.page(new Page<>(page, pageSize));
        log.info("query result [sysMachineEntity] done,size:{} ", result.getRecords().size());
        return result;
    }

    @Override
    public SysMachineEntity getById(int id) {
        log.info("query sysMachineEntity id:{}", id);
        SysMachineEntity sysMachineEntity = super.getById(id);
        return sysMachineEntity;
    }

    @Override
    public int insert(SysMachineEntity sysMachineEntity) {
        if (super.save(sysMachineEntity)) {
            log.info("insert sysMachineEntity done,id:{}", sysMachineEntity.getPrimaryKey());
            return sysMachineEntity.getPrimaryKey();
        } else {
            log.error("insert sysMachineEntity failed");
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
            log.error("delete id:{} sysMachineEntity failed", id);
            throw new BizException("delete failed[id=" + id + "]");
        }
    }

    @Override
    public int update(SysMachineEntity sysMachineEntity) {
        log.info("update id:{}", sysMachineEntity.getPrimaryKey());
        if (super.updateById(sysMachineEntity)) {
            log.info("update id:{} done", sysMachineEntity.getPrimaryKey());
            return sysMachineEntity.getPrimaryKey();
        } else {
            log.error("update id:{} failed", sysMachineEntity.getPrimaryKey());
            throw new BizException("update failed [id=" + sysMachineEntity.getPrimaryKey() + "]");
        }
    }

}
