package com.caveup.barcode.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.caveup.barcode.model.CustomerConfigEntity;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xw80329
 * @since 2022-01-09
 */
public interface CustomerConfigRepository {

    /**
     * 分页查询CustomerConfigEntity
     *
     * @param page     当前页数
     * @param pageSize 页的大小
     * @return 返回mybatis-plus的Page对象,其中records字段为符合条件的查询结果
     * @author xw80329
     * @since 2022-01-09
     */
    Page<CustomerConfigEntity> listByPage(int page, int pageSize);

    /**
     * 根据id查询CustomerConfigEntity
     *
     * @param id 需要查询的CustomerConfigEntity的id
     * @return 返回对应id的CustomerConfigEntity对象
     * @author xw80329
     * @since 2022-01-09
     */
    CustomerConfigEntity getById(int id);

    /**
     * 插入CustomerConfigEntity
     *
     * @param customerConfigEntity 需要插入的CustomerConfigEntity对象
     * @return 返回插入成功之后CustomerConfigEntity对象的id
     * @author xw80329
     * @since 2022-01-09
     */
    int insert(CustomerConfigEntity customerConfigEntity);

    /**
     * 根据id删除CustomerConfigEntity
     *
     * @param id 需要删除的CustomerConfigEntity对象的id
     * @return 返回被删除的CustomerConfigEntity对象的id
     * @author xw80329
     * @since 2022-01-09
     */
    int deleteById(int id);

    /**
     * 根据id更新CustomerConfigEntity
     *
     * @param customerConfigEntity 需要更新的CustomerConfigEntity对象
     * @return 返回被更新的CustomerConfigEntity对象的id
     * @author xw80329
     * @since 2022-01-09
     */
    int update(CustomerConfigEntity customerConfigEntity);

}
