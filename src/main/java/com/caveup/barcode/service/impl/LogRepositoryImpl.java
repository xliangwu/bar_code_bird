package com.caveup.barcode.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.caveup.barcode.exception.BizException;
import com.caveup.barcode.mapper.LogMapper;
import com.caveup.barcode.model.LogEntity;
import com.caveup.barcode.service.LogRepository;
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
public class LogRepositoryImpl extends ServiceImpl<LogMapper, LogEntity> implements LogRepository {

    @Override
    public Page<LogEntity> listByPage(int page, int pageSize) {
        log.info("query logEntity: page = {} pageSize = {}", page, pageSize);
        Page<LogEntity> result = super.page(new Page<>(page, pageSize));
        log.info("query result [logEntity] done,size:{} ", result.getRecords().size());
        return result;
    }

    @Override
    public LogEntity getById(int id) {
        log.info("query logEntity id:{}", id);
        LogEntity logEntity = super.getById(id);
        return logEntity;
    }

    @Override
    public int insert(LogEntity logEntity) {
        if (super.save(logEntity)) {
            log.info("insert logEntity done,id:{}", logEntity.getPrimaryKey());
            return logEntity.getPrimaryKey();
        } else {
            log.error("insert logEntity failed");
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
            log.error("delete id:{} logEntity failed", id);
            throw new BizException("delete failed[id=" + id + "]");
        }
    }

    @Override
    public int update(LogEntity logEntity) {
        log.info("update id:{}", logEntity.getPrimaryKey());
        if (super.updateById(logEntity)) {
            log.info("update id:{} done", logEntity.getPrimaryKey());
            return logEntity.getPrimaryKey();
        } else {
            log.error("update id:{} failed", logEntity.getPrimaryKey());
            throw new BizException("update failed [id=" + logEntity.getPrimaryKey() + "]");
        }
    }

}
