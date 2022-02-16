package com.caveup.barcode.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.caveup.barcode.model.SysMachineEntity;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xw80329
 * @since 2022-01-09
 */
public interface SysMachineRepository extends IService<SysMachineEntity> {

    /**
     * 分页查询SysMachineEntity
     *
     * @param page     当前页数
     * @param pageSize 页的大小
     * @return 返回mybatis-plus的Page对象,其中records字段为符合条件的查询结果
     * @author xw80329
     * @since 2022-01-09
     */
    Page<SysMachineEntity> listByPage(int page, int pageSize);

    /**
     * 根据id查询SysMachineEntity
     *
     * @param id 需要查询的SysMachineEntity的id
     * @return 返回对应id的SysMachineEntity对象
     * @author xw80329
     * @since 2022-01-09
     */
    SysMachineEntity getById(int id);

    /**
     * 插入SysMachineEntity
     *
     * @param sysMachineEntity 需要插入的SysMachineEntity对象
     * @return 返回插入成功之后SysMachineEntity对象的id
     * @author xw80329
     * @since 2022-01-09
     */
    int insert(SysMachineEntity sysMachineEntity);

    /**
     * 根据id删除SysMachineEntity
     *
     * @param id 需要删除的SysMachineEntity对象的id
     * @return 返回被删除的SysMachineEntity对象的id
     * @author xw80329
     * @since 2022-01-09
     */
    int deleteById(int id);

    /**
     * 根据id更新SysMachineEntity
     *
     * @param sysMachineEntity 需要更新的SysMachineEntity对象
     * @return 返回被更新的SysMachineEntity对象的id
     * @author xw80329
     * @since 2022-01-09
     */
    int update(SysMachineEntity sysMachineEntity);

}
