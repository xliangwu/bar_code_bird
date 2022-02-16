package com.caveup.barcode.controller.biz;


import com.alibaba.fastjson.JSONObject;
import com.caveup.barcode.result.helper.ApiResultHelper;
import com.caveup.barcode.result.model.ApiResultModel;
import com.caveup.barcode.vo.UserVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
@RequestMapping("/api/v1/user")
public class UserController {

    /**
     * 新增
     */
    @PostMapping("/login")
    public ApiResultModel<?> login(@RequestBody @Valid UserVO vo) {
        return ApiResultHelper.success("admin-token");
    }

    @GetMapping("/info")
    public ApiResultModel<?> queryInfo() {
        String ss = "{\n" +
                "        roles: ['admin'],\n" +
                "        introduction: 'I am a super administrator',\n" +
                "        avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',\n" +
                "        name: 'Super Admin'\n" +
                "    }";

        JSONObject obj = JSONObject.parseObject(ss);
        return ApiResultHelper.success(obj);
    }

    @PostMapping("/logout")
    public ApiResultModel<?> logout() {
        return ApiResultHelper.success();
    }
}
