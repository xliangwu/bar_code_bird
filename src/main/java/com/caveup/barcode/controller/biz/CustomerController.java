package com.caveup.barcode.controller.biz;


import com.caveup.barcode.model.CustomerEntity;
import com.caveup.barcode.result.helper.ApiResultHelper;
import com.caveup.barcode.result.model.ApiResultModel;
import com.caveup.barcode.service.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/api/v1/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * 查询分页数据
     */
    @RequestMapping(method = RequestMethod.GET)
    public ApiResultModel<?> listByPage(@RequestParam(name = "page", defaultValue = "1") int page,
                                        @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        return ApiResultHelper.success(customerRepository.listByPage(page, pageSize));
    }


    /**
     * 根据id查询
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ApiResultModel<?> getById(@PathVariable("id") Integer id) {
        return ApiResultHelper.success(customerRepository.getById(id));
    }

    /**
     * 新增
     */
    @RequestMapping(method = RequestMethod.POST)
    public ApiResultModel<?> insert(@RequestBody CustomerEntity customerEntity) {
        return ApiResultHelper.success(customerRepository.insert(customerEntity));
    }

    /**
     * 删除
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ApiResultModel<?> deleteById(@PathVariable("id") Integer id) {
        return ApiResultHelper.success(customerRepository.deleteById(id));
    }

    /**
     * 修改
     */
    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public ApiResultModel<?> updateById(@RequestBody CustomerEntity customerEntity) {
        return ApiResultHelper.success(customerRepository.update(customerEntity));
    }
}
