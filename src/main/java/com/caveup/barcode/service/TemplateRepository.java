package com.caveup.barcode.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.caveup.barcode.model.TemplateEntity;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xw80329
 * @since 2022-01-09
 */
public interface TemplateRepository {

    /**
     * 分页查询TemplateEntity
     *
     * @param page     当前页数
     * @param pageSize 页的大小
     * @return 返回mybatis-plus的Page对象,其中records字段为符合条件的查询结果
     * @author xw80329
     * @since 2022-01-09
     */
    Page<TemplateEntity> listByPage(int page, int pageSize);

    /**
     * 根据id查询TemplateEntity
     *
     * @param id 需要查询的TemplateEntity的id
     * @return 返回对应id的TemplateEntity对象
     * @author xw80329
     * @since 2022-01-09
     */
    TemplateEntity getById(int id);

    /**
     * 插入TemplateEntity
     *
     * @param templateEntity 需要插入的TemplateEntity对象
     * @return 返回插入成功之后TemplateEntity对象的id
     * @author xw80329
     * @since 2022-01-09
     */
    int insert(TemplateEntity templateEntity);

    /**
     * 根据id删除TemplateEntity
     *
     * @param id 需要删除的TemplateEntity对象的id
     * @return 返回被删除的TemplateEntity对象的id
     * @author xw80329
     * @since 2022-01-09
     */
    int deleteById(int id);

    /**
     * 根据id更新TemplateEntity
     *
     * @param templateEntity 需要更新的TemplateEntity对象
     * @return 返回被更新的TemplateEntity对象的id
     * @author xw80329
     * @since 2022-01-09
     */
    int update(TemplateEntity templateEntity);

}
