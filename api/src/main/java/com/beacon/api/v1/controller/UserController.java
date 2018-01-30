package com.beacon.api.v1.controller;

import com.beacon.commons.base.BaseController;
import com.beacon.commons.response.ResData;
import com.beacon.commons.utils.AssertUtils;
import com.beacon.entity.User;
import com.beacon.enums.code.UserResCode;
import com.beacon.enums.dict.SmsLogDict;
import com.beacon.global.session.TokenManager;
import com.beacon.global.session.TokenModel;
import com.beacon.service.SmsService;
import com.beacon.service.UserService;
import com.beacon.utils.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Map;

import static com.beacon.enums.code.UserResCode.CODE_ERROR;
import static com.beacon.enums.code.UserResCode.MOBILE_EXIST;

/**
 * 用户管理
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/10/10
 */
@RestController
@RequestMapping("api/v1/user")
@Api(value = "api/v1/user", tags = "用户管理")
public class UserController extends BaseController {

    @Inject
    private UserService userService;

    @Inject
    private TokenManager tokenManager;

    @Inject
    private SmsService smsService;

    @ApiOperation(value = "用户注册", notes = "第一步，输入手机号，验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号", required = true, paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "code", value = "验证码", required = true, paramType = "form", dataType = "string"),
    })
    @PostMapping("register/first/step")
    public ResData registerFirstStep(@RequestParam String mobile,
                                     @RequestParam String code) {
        AssertUtils.isMobile(UserResCode.MOBILE_FORMAT_ERROR, mobile);
        AssertUtils.length(CODE_ERROR, 6, code);

        //校验手机号
        User user = userService.findByMobile(mobile);
        AssertUtils.isNull(MOBILE_EXIST, user);
        // 校验手机验证码
        AssertUtils.isTrue(CODE_ERROR, smsService.checkMobileCode(mobile, code, SmsLogDict.TYPE_REGISTER));

        return ResData.build(userService.registerFirstStep(mobile));
    }

    @ApiOperation(value = "用户注册", notes = "第二步，获取钱包密钥和地址，更换钱包密钥也调用此接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号", required = true, paramType = "form", dataType = "string"),
    })
    @PostMapping("register/second/step")
    public ResData<Map<String, Object>> registerSecondStep(@RequestParam String mobile) {
        AssertUtils.isMobile(UserResCode.MOBILE_FORMAT_ERROR, mobile);

        return userService.registerSecondStep(mobile);
    }

    @ApiOperation(value = "用户注册", notes = "第三步，输入昵称和钱包密钥校验")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号", required = true, paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "nickname", value = "昵称", required = true, paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "publicKey", value = "钱包公钥需要根据用户提供的密码在在本地用程序生成", required = true, paramType = "form", dataType = "string"),
    })
    @PostMapping("register/third/step")
    public ResData<String> registerThirdStep(@RequestParam String mobile,
                                             @RequestParam String nickname,
                                             @RequestParam String publicKey) {
        AssertUtils.isMobile(UserResCode.MOBILE_FORMAT_ERROR, mobile);

        return userService.registerThirdStep(mobile, nickname, publicKey);
    }

    @ApiOperation(value = "用户登录", notes = "用户输入手机号/邮箱、钱包密钥登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "手机号/邮箱", required = true, paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "publicKey", value = "钱包公钥需要根据用户提供的密码在在本地用程序生成", required = true, paramType = "form", dataType = "string"),
    })
    @PostMapping("login")
    public ResData<String> login(@RequestParam String username,
                                 @RequestParam String publicKey) {
        return userService.login(username, publicKey);
    }

    @ApiOperation(value = "用户登出")
    @GetMapping("logout")
    public ResData logout() {
        tokenManager.delToken(ShiroUtils.getUserId(), TokenModel.TYPE_API);
        ShiroUtils.logout();
        return ResData.success();
    }



    @ApiOperation(value = "下载token", notes = "将参数中的token值写入文件并下载")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "token", required = true, paramType = "form", dataType = "string"),
    })
    @PostMapping("download/token")
    public ResData<String> login(@RequestParam String token) {

    }
;


}
