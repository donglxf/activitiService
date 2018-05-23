package com.ht.commonactivity.controller;


import com.ht.commonactivity.common.result.Result;
import com.ht.ussp.bean.LoginUserInfoHelper;
import com.ht.ussp.client.dto.LoginInfoDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhangpeng
 * @since 2018-01-22
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    public LoginUserInfoHelper userInfoHelper;

    @GetMapping("getUserInfo")
    @ApiOperation(value = "获取登录用户信息")
    public Result<LoginInfoDto> getLoginInfo() {
//        LoginInfoDto user = userInfoHelper.getLoginInfo();
//        return Result.success(user);
        return null;
    }


}

