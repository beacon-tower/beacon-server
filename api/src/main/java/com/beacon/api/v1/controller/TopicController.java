package com.beacon.api.v1.controller;

import com.beacon.commons.base.BaseController;
import com.beacon.commons.response.ResData;
import com.beacon.pojo.TopicDtoList;
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
public class TopicController extends BaseController {

    @Inject
    private TopicService topicService;

    @ApiOperation(value = "添加话题", notes = "用于系统管理员添加话题")
    @PostMapping("")
    public ResData create(@ApiParam(name = "topicDto", value = "话题dto") @RequestBody TopicInputDto topicInputDto) {
        topicService.add(topicInputDto);
        return ResData.success();
    }

    @ApiOperation(value = "话题名列表", notes = "排序用户是否关注、话题被关注数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", value = "显示多少条,默认显示所有", paramType = "query", dataType = "int"),
    })
    @GetMapping("list")
    public ResData<List<TopicDtoList>> list(@RequestParam(required = false, name = "limit") Integer top) {
        List<TopicDtoList> topicsDtoList = topicService.findList(ShiroUtils.getUserId(), top);
        return ResData.success(topicsDtoList);
    }

    @ApiOperation(value = "话题列表", notes = "选择更多话题,显示更全的话题信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", value = "显示多少条,默认显示所有", paramType = "query", dataType = "int"),
    })
    @GetMapping("more/list")
    public ResData<List<TopicOutputDto>> moreList(@RequestParam(required = false, name = "limit") Integer top) {
        List<TopicOutputDto> topicList = topicService.findMoreList(ShiroUtils.getUserId(), top);
        return ResData.success(topicList);
    }
}

