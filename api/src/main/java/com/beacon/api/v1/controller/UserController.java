package com.beacon.api.v1.controller;

import com.beacon.commons.response.ResData;
import com.beacon.commons.utils.AssertUtils;
import com.beacon.global.session.TokenManager;
import com.beacon.global.session.TokenModel;
import com.beacon.service.UserService;
import com.beacon.utils.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

import static com.beacon.commons.code.PublicResCode.PARAMS_IS_NULL;
import static com.beacon.enums.code.UserResCode.PASSWORD_FORMAT_ERROR;
import static com.beacon.enums.code.UserResCode.PASSWORD_NOT_SAME;

/**
 * 用户管理
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/10/10
 */
@RestController
@RequestMapping("api/v1/user")
@Api(value = "api/v1/user", description = "用户管理")
public class UserController {

    @Inject
    private UserService userService;

    @Inject
    private TokenManager tokenManager;

    @ApiOperation("用户注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "账号", required = true, paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "password", value = "登录密码", required = true, paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "repeatPassword", value = "重复密码", required = true, paramType = "form", dataType = "string"),
    })
    @PostMapping("register")
    public ResData register(@RequestParam String username,
                            @RequestParam String password,
                            @RequestParam String repeatPassword) {

        AssertUtils.notNull(PARAMS_IS_NULL, new String[]{"username", "password", "repeatPassword"},
                username, password, repeatPassword);
        AssertUtils.length(PASSWORD_FORMAT_ERROR, 8, 32, password);
        AssertUtils.isTrue(PASSWORD_NOT_SAME, password.equals(repeatPassword));

        userService.register(username, password);

        return this.login(username, password);
    }

    @ApiOperation(value = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "账号", required = true, paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "form", dataType = "string"),
    })
    @PostMapping("login")
    public ResData login(@RequestParam String username,
                         @RequestParam String password) {

        AssertUtils.notNull(PARAMS_IS_NULL, new String[]{"username", "password"}, username, password);
        AssertUtils.length(PASSWORD_FORMAT_ERROR, 8, 32, password);

        //用户登录
        Integer userId = userService.login(username, password);

        String token = tokenManager.createToken(userId, TokenModel.TYPE_API);

        ResData resData = ResData.buildSuccess();
        resData.putData("token", token);
        return resData;
    }

    @ApiOperation(value = "用户信息")
    @GetMapping("info")
    public ResData userInfo() {
        ResData resData = ResData.buildSuccess();
        resData.putData("user", ShiroUtils.getUser());
        return resData;
    }

    @ApiOperation(value = "用户登出")
    @GetMapping("logout")
    public ResData logout() {
        tokenManager.delToken(ShiroUtils.getUserId(), TokenModel.TYPE_API);
        ShiroUtils.logout();
        return ResData.buildSuccess();
    }
}
