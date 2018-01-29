package com.beacon.api.v1.controller;

import com.beacon.commons.response.ResData;
import com.beacon.commons.utils.DateUtils;
import com.beacon.entity.User;
import com.beacon.pojo.PostsListOutDto;
import com.beacon.pojo.TopicOutputDto;
import com.beacon.service.IndexService;
import com.beacon.service.TopicService;
import com.beacon.service.UserService;
import com.beacon.utils.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Ian.Su
 * @version $Id IndexController.java, v 0.1 2018/1/25 10:37 Ian.Su Exp $
 **/
@RestController
@RequestMapping("api/v1/index")
@Api(value = "api/v1/index", tags = "首页")
public class IndexController {

    @Inject
    private IndexService indexService;

    @Inject
    private TopicService topicService;

    @Inject
    private UserService userService;


    @ApiOperation(value = "默认文章列表", notes = "首页的文章列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", value = "页码,默认显示第1页", defaultValue = "1", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "显示多少条,默认显示10条", defaultValue = "10", paramType = "query", dataType = "int")
    })
    @GetMapping("posts/list")
    public ResData<List<PostsListOutDto>> getPostsList(@RequestParam(defaultValue = "1") Integer pageNumber,
                                                       @RequestParam(defaultValue = "10") Integer pageSize) {

        User user = ShiroUtils.getUser();
        if (user == null) {
            return ResData.success(indexService.findPostsByHot(null, pageNumber, pageSize));
        }

        return ResData.success(null);
    }


    @ApiOperation(value = "根据话题ID查询文章列表", notes = "根据话题ID查询文章列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "topicId", value = "话题ID", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageNumber", value = "页码,默认显示第1页", defaultValue = "1", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "显示多少条,默认显示10条", defaultValue = "10", paramType = "query", dataType = "int")
    })
    @GetMapping("topic/posts/list")
    public ResData<List<PostsListOutDto>> getPostsListByTopic(@RequestParam Integer topicId,
                                                              @RequestParam(defaultValue = "1") Integer pageNumber,
                                                              @RequestParam(defaultValue = "10") Integer pageSize) {

        User user = ShiroUtils.getUser();
        if (user == null) {
            return ResData.success(indexService.findPostsByHot(topicId, pageNumber, pageSize));
        }

        return ResData.success(null);
    }


    @ApiOperation(value = "话题列表", notes = "话题列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", value = "显示多少条,默认显示10条", defaultValue = "10", paramType = "query", dataType = "int")
    })
    @GetMapping("topic/list")
    public ResData<List<TopicOutputDto>> getTopicList(@RequestParam(defaultValue = "10") Integer limit) {

        User user = ShiroUtils.getUser();

        if (user != null) {
            List<TopicOutputDto> list = topicService.findMoreList(user.getId(), limit);
            return ResData.success(list);
        }

        return ResData.success(topicService.findList(limit));
    }


    @ApiOperation(value = "推荐作者", notes = "推荐作者")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", value = "页码,默认显示第1页", defaultValue = "1", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "显示多少条,默认显示10条", defaultValue = "10", paramType = "query", dataType = "int")
    })
    @GetMapping("author/list")
    public ResData<List<User>> getAuthorList(@RequestParam(defaultValue = "1") Integer pageNumber,
                                             @RequestParam(defaultValue = "10") Integer pageSize) {

        User user = ShiroUtils.getUser();

        if (user == null) {
            return ResData.success(userService.findUsers(pageNumber, pageSize));
        }

        return ResData.success(userService.findUsersNotFollow(user.getId(), pageNumber, pageSize));
    }


    @ApiOperation(value = "今日最火", notes = "今日最火查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", value = "页码,默认显示第1页", defaultValue = "1", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "显示多少条,默认显示10条", defaultValue = "10", paramType = "query", dataType = "int")
    })
    @GetMapping("today/hot/list")
    public ResData<List<PostsListOutDto>> getTodayHotList(@RequestParam(defaultValue = "1") Integer pageNumber,
                                                          @RequestParam(defaultValue = "10") Integer pageSize) {


        Integer startDate = Integer.parseInt(DateUtils.format(new Date(), "yyyyMMdd"));

        List list = indexService.findPostsByDate(startDate, pageNumber, pageSize);

        return ResData.success(list);

    }


    @ApiOperation(value = "7日最火", notes = "7日最火查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", value = "页码,默认显示第1页", defaultValue = "1", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "显示多少条,默认显示10条", defaultValue = "10", paramType = "query", dataType = "int")
    })
    @GetMapping("seven/day/hot/list")
    public ResData<List<PostsListOutDto>> getSevenDayHotList(@RequestParam(defaultValue = "1") Integer pageNumber,
                                                             @RequestParam(defaultValue = "10") Integer pageSize) {

        Date date = DateUtils.add(new Date(), Calendar.DAY_OF_MONTH, -7);

        Integer startDate = Integer.parseInt(DateUtils.format(date, "yyyyMMdd"));

        List list = indexService.findPostsByDate(startDate, pageNumber, pageSize);

        return ResData.success(list);

    }


    @ApiOperation(value = "历史最火", notes = "历史最火查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", value = "页码,默认显示第1页", defaultValue = "1", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "limit", value = "显示多少条,默认显示10条", defaultValue = "10", paramType = "query", dataType = "int")
    })
    @GetMapping("history/hot/list")
    public ResData<List<PostsListOutDto>> getHistoryHotList(@RequestParam(defaultValue = "1") Integer pageNumber,
                                                            @RequestParam(defaultValue = "10") Integer limit) {

        List list = indexService.findPostsByDate(null, pageNumber, limit);

        return ResData.success(list);

    }


}
