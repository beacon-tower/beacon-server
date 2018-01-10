package com.beacon.api.v1.controller;

import com.beacon.commons.response.ResData;
import com.beacon.commons.utils.AssertUtils;
import com.beacon.commons.utils.StringUtils;
import com.beacon.enums.code.UserResCode;
import com.beacon.mapper.UserMapper;
import com.beacon.pojo.UserInfoDto;
import com.beacon.service.UserService;
import com.beacon.utils.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * 用户信息管理
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/10/10
 */
@RestController
@RequestMapping("api/v1/user/info")
@Api(value = "api/v1/user/info", tags = "用户信息管理")
public class UserInfoController {

    @Inject
    private UserService userService;

    @Inject
    private UserMapper userMapper;

    @ApiOperation(value = "用户信息", response = UserInfoDto.class)
    @GetMapping()
    public ResData<UserInfoDto> detail() {
        UserInfoDto userInfoDto = userMapper.toInfoDto(ShiroUtils.getUser());
        return ResData.success(userInfoDto);
    }

    @ApiOperation(value = "用户信息编辑")
    @PutMapping()
    public ResData edit(@ApiParam(name = "userInfoDto", value = "用户信息dto")
                        @RequestBody UserInfoDto userInfoDto) {

        AssertUtils.isMobile(UserResCode.MOBILE_FORMAT_ERROR, userInfoDto.getMobile());
        if (StringUtils.isNotEmpty(userInfoDto.getEmail())) {
            AssertUtils.isEmail(UserResCode.EMAIL_FORMAT_ERROR, userInfoDto.getEmail());
        }

        userService.editInfo(userInfoDto);
        return ResData.success();
    }

}
