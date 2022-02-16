package com.caveup.barcode.controller.biz;


import com.caveup.barcode.model.SysMachineEntity;
import com.caveup.barcode.result.helper.ApiResultHelper;
import com.caveup.barcode.result.model.ApiResultModel;
import com.caveup.barcode.service.SysMachineRepository;
import com.caveup.barcode.vo.SysMachineVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xw80329
 * @version v1.0
 * @since 2022-01-09
 */
@RestController
@RequestMapping("/api/v1/sysMachine")
public class SysMachineController {

    @Autowired
    private SysMachineRepository sysMachineRepository;

    /**
     * 查询分页数据
     */
    @RequestMapping(method = RequestMethod.GET)
    public ApiResultModel<?> listByPage(@RequestParam(name = "page", defaultValue = "1") int page,
                                        @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        return ApiResultHelper.success(sysMachineRepository.listByPage(page, pageSize));
    }

    /**
     * 根据id查询
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ApiResultModel<?> getById(@PathVariable("id") Integer id) {
        return ApiResultHelper.success(sysMachineRepository.getById(id));
    }

    /**
     * 新增
     */
    @RequestMapping(method = RequestMethod.POST, value = "/save")
    public ApiResultModel<?> insert(@RequestBody @Validated SysMachineVO vo) {
        SysMachineEntity entity = new SysMachineEntity();
        entity.setMachineCategory(vo.getMachineCategory());
        entity.setCreatedTime(new Date());
        entity.setMachineName(vo.getMachineName());
        return ApiResultHelper.success(sysMachineRepository.insert(entity));
    }

    /**
     * 删除
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ApiResultModel<?> deleteById(@PathVariable("id") Integer id) {
        return ApiResultHelper.success(sysMachineRepository.deleteById(id));
    }

    /**
     * 修改
     */
    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public ApiResultModel<?> updateById(@RequestBody @Validated SysMachineVO vo) {
        SysMachineEntity entity = new SysMachineEntity();
        entity.setMachineCategory(vo.getMachineCategory());
        entity.setId(vo.getId());
        entity.setCreatedTime(new Date());
        entity.setMachineName(vo.getMachineName());
        return ApiResultHelper.success(sysMachineRepository.update(entity));
    }
}
