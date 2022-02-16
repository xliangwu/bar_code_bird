package com.caveup.barcode.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.caveup.barcode.model.CustomerStorageEntity;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xw80329
 * @since 2022-01-09
 */
public interface CustomerStorageRepository extends IService<CustomerStorageEntity> {

    /**
     * 分页查询CustomerStorageEntity
     *
     * @param page     当前页数
     * @param pageSize 页的大小
     * @return 返回mybatis-plus的Page对象,其中records字段为符合条件的查询结果
     * @author xw80329
     * @since 2022-01-09
     */
    Page<CustomerStorageEntity> listByPage(int page, int pageSize);

    /**
     * 根据id查询CustomerStorageEntity
     *
     * @param id 需要查询的CustomerStorageEntity的id
     * @return 返回对应id的CustomerStorageEntity对象
     * @author xw80329
     * @since 2022-01-09
     */
    CustomerStorageEntity getById(int id);

    /**
     * 插入CustomerStorageEntity
     *
     * @param customerStorageEntity 需要插入的CustomerStorageEntity对象
     * @return 返回插入成功之后CustomerStorageEntity对象的id
     * @author xw80329
     * @since 2022-01-09
     */
    int insert(CustomerStorageEntity customerStorageEntity);

    /**
     * 根据id删除CustomerStorageEntity
     *
     * @param id 需要删除的CustomerStorageEntity对象的id
     * @return 返回被删除的CustomerStorageEntity对象的id
     * @author xw80329
     * @since 2022-01-09
     */
    int deleteById(int id);

    /**
     * 根据id更新CustomerStorageEntity
     *
     * @param customerStorageEntity 需要更新的CustomerStorageEntity对象
     * @return 返回被更新的CustomerStorageEntity对象的id
     * @author xw80329
     * @since 2022-01-09
     */
    int update(CustomerStorageEntity customerStorageEntity);

}
