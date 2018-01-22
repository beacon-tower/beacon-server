package com.beacon.api.v1.controller;

import com.beacon.commons.base.BaseController;
import com.beacon.commons.response.ResData;
import com.beacon.commons.utils.AssertUtils;
import com.beacon.enums.code.UserResCode;
import com.beacon.service.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * 发送短信
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/10/12
 */
@RestController
@RequestMapping("api/v1/sms")
@Api(value = "api/v1/sms", tags = "发送短信")
public class SmsController extends BaseController {

    @Inject
    private SmsService smsService;

    @ApiOperation(value = "发送短信验证码", response = ResData.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号", required = true, paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "type", value = "短信类型", allowableValues = "register",
                    required = true, paramType = "form", dataType = "string"),
    })
    @PostMapping("code")
    public ResData sendMobileCode(@RequestParam String mobile,
                                  @RequestParam String type) {

        AssertUtils.isMobile(UserResCode.MOBILE_FORMAT_ERROR, mobile);
        return ResData.build(smsService.sendMobileCode(mobile, type));
    }
}
