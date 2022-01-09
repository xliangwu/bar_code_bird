package com.caveup.barcode.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.caveup.barcode.model.CustomerEntity;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xw80329
 * @since 2022-01-09
 */
public interface CustomerRepository {

    /**
     * 分页查询CustomerEntity
     *
     * @param page     当前页数
     * @param pageSize 页的大小
     * @return 返回mybatis-plus的Page对象,其中records字段为符合条件的查询结果
     * @author xw80329
     * @since 2022-01-09
     */
    Page<CustomerEntity> listByPage(int page, int pageSize);

    /**
     * 根据id查询CustomerEntity
     *
     * @param id 需要查询的CustomerEntity的id
     * @return 返回对应id的CustomerEntity对象
     * @author xw80329
     * @since 2022-01-09
     */
    CustomerEntity getById(int id);

    /**
     * 插入CustomerEntity
     *
     * @param customerEntity 需要插入的CustomerEntity对象
     * @return 返回插入成功之后CustomerEntity对象的id
     * @author xw80329
     * @since 2022-01-09
     */
    int insert(CustomerEntity customerEntity);

    /**
     * 根据id删除CustomerEntity
     *
     * @param id 需要删除的CustomerEntity对象的id
     * @return 返回被删除的CustomerEntity对象的id
     * @author xw80329
     * @since 2022-01-09
     */
    int deleteById(int id);

    /**
     * 根据id更新CustomerEntity
     *
     * @param customerEntity 需要更新的CustomerEntity对象
     * @return 返回被更新的CustomerEntity对象的id
     * @author xw80329
     * @since 2022-01-09
     */
    int update(CustomerEntity customerEntity);

}
