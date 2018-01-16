package com.beacon.api.v1.controller;

import com.beacon.commons.response.ResData;
import com.beacon.pojo.TopicInputDto;
import com.beacon.pojo.TopicOutputDto;
import com.beacon.service.TopicService;
import com.beacon.utils.ShiroUtils;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * 话题
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/16
 */
@RestController
@RequestMapping("api/v1/topic")
@Api(value = "api/v1/topic", tags = "话题")
public class TopicController {

    @Inject
    private TopicService topicService;

    @ApiOperation(value = "添加话题", notes = "用于系统管理员添加话题", response = ResData.class)
    @PostMapping("")
    public ResData create(@ApiParam(name = "topicDto", value = "话题dto") @RequestBody TopicInputDto topicInputDto) {
        topicService.add(topicInputDto);
        return ResData.success();
    }

    @ApiOperation(value = "话题名列表", notes = "排序用户是否关注、话题被关注数", response = String.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", value = "显示多少条", defaultValue = "12", paramType = "form", dataType = "int"),
    })
    @GetMapping("name/list")
    public ResData<List<String>> nameList(@RequestParam(defaultValue = "12") Integer limit) {
        Integer userId = ShiroUtils.getUserId();
        List<String> topicNameList = topicService.findNameListByUserId(userId, limit);
        return ResData.success(topicNameList);
    }

    @ApiOperation(value = "话题列表", notes = "选择更多话题", response = TopicOutputDto.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", value = "显示多少条", paramType = "form", dataType = "int"),
    })
    @GetMapping("list")
    public ResData<List<TopicOutputDto>> list(@RequestParam(required = false) Integer limit) {
        Integer userId = ShiroUtils.getUserId();
        List<TopicOutputDto> topicList = topicService.findListByUserId(userId, limit);
        return ResData.success(topicList);
    }
}

