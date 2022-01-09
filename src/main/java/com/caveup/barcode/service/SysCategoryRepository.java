package com.caveup.barcode.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.caveup.barcode.model.SysCategoryEntity;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xw80329
 * @since 2022-01-09
 */
public interface SysCategoryRepository {

    /**
     * 分页查询SysCategoryEntity
     *
     * @param page     当前页数
     * @param pageSize 页的大小
     * @return 返回mybatis-plus的Page对象,其中records字段为符合条件的查询结果
     * @author xw80329
     * @since 2022-01-09
     */
    Page<SysCategoryEntity> listByPage(int page, int pageSize);

    /**
     * 根据id查询SysCategoryEntity
     *
     * @param id 需要查询的SysCategoryEntity的id
     * @return 返回对应id的SysCategoryEntity对象
     * @author xw80329
     * @since 2022-01-09
     */
    SysCategoryEntity getById(int id);

    /**
     * 插入SysCategoryEntity
     *
     * @param sysCategoryEntity 需要插入的SysCategoryEntity对象
     * @return 返回插入成功之后SysCategoryEntity对象的id
     * @author xw80329
     * @since 2022-01-09
     */
    int insert(SysCategoryEntity sysCategoryEntity);

    /**
     * 根据id删除SysCategoryEntity
     *
     * @param id 需要删除的SysCategoryEntity对象的id
     * @return 返回被删除的SysCategoryEntity对象的id
     * @author xw80329
     * @since 2022-01-09
     */
    int deleteById(int id);

    /**
     * 根据id更新SysCategoryEntity
     *
     * @param sysCategoryEntity 需要更新的SysCategoryEntity对象
     * @return 返回被更新的SysCategoryEntity对象的id
     * @author xw80329
     * @since 2022-01-09
     */
    int update(SysCategoryEntity sysCategoryEntity);

}
