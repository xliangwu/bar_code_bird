package com.caveup.barcode.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.caveup.barcode.model.LogEntity;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xw80329
 * @since 2022-01-09
 */
public interface LogRepository {

    /**
     * 分页查询LogEntity
     *
     * @param page     当前页数
     * @param pageSize 页的大小
     * @return 返回mybatis-plus的Page对象,其中records字段为符合条件的查询结果
     * @author xw80329
     * @since 2022-01-09
     */
    Page<LogEntity> listByPage(int page, int pageSize);

    /**
     * 根据id查询LogEntity
     *
     * @param id 需要查询的LogEntity的id
     * @return 返回对应id的LogEntity对象
     * @author xw80329
     * @since 2022-01-09
     */
    LogEntity getById(int id);

    /**
     * 插入LogEntity
     *
     * @param logEntity 需要插入的LogEntity对象
     * @return 返回插入成功之后LogEntity对象的id
     * @author xw80329
     * @since 2022-01-09
     */
    int insert(LogEntity logEntity);

    /**
     * 根据id删除LogEntity
     *
     * @param id 需要删除的LogEntity对象的id
     * @return 返回被删除的LogEntity对象的id
     * @author xw80329
     * @since 2022-01-09
     */
    int deleteById(int id);

    /**
     * 根据id更新LogEntity
     *
     * @param logEntity 需要更新的LogEntity对象
     * @return 返回被更新的LogEntity对象的id
     * @author xw80329
     * @since 2022-01-09
     */
    int update(LogEntity logEntity);

}
