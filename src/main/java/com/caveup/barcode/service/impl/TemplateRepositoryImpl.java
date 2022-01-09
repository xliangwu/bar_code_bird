package com.caveup.barcode.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.caveup.barcode.exception.BizException;
import com.caveup.barcode.mapper.TemplateMapper;
import com.caveup.barcode.model.TemplateEntity;
import com.caveup.barcode.service.TemplateRepository;
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
public class TemplateRepositoryImpl extends ServiceImpl<TemplateMapper, TemplateEntity> implements TemplateRepository {

    @Override
    public Page<TemplateEntity> listByPage(int page, int pageSize) {
        log.info("query templateEntity: page = {} pageSize = {}", page, pageSize);
        Page<TemplateEntity> result = super.page(new Page<>(page, pageSize));
        log.info("query result [templateEntity] done,size:{} ", result.getRecords().size());
        return result;
    }

    @Override
    public TemplateEntity getById(int id) {
        log.info("query templateEntity id:{}", id);
        TemplateEntity templateEntity = super.getById(id);
        return templateEntity;
    }

    @Override
    public int insert(TemplateEntity templateEntity) {
        if (super.save(templateEntity)) {
            log.info("insert templateEntity done,id:{}", templateEntity.getPrimaryKey());
            return templateEntity.getPrimaryKey();
        } else {
            log.error("insert templateEntity failed");
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
            log.error("delete id:{} templateEntity failed", id);
            throw new BizException("delete failed[id=" + id + "]");
        }
    }

    @Override
    public int update(TemplateEntity templateEntity) {
        log.info("update id:{}", templateEntity.getPrimaryKey());
        if (super.updateById(templateEntity)) {
            log.info("update id:{} done", templateEntity.getPrimaryKey());
            return templateEntity.getPrimaryKey();
        } else {
            log.error("update id:{} failed", templateEntity.getPrimaryKey());
            throw new BizException("update failed [id=" + templateEntity.getPrimaryKey() + "]");
        }
    }

}
