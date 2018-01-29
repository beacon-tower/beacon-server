package com.beacon.api.v1.controller;

import com.beacon.commons.base.BaseController;
import com.beacon.commons.response.ResData;
import com.beacon.pojo.PageResult;
import com.beacon.service.UserFavoriteService;
import com.beacon.utils.PageUtils;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * 我的
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/24
 */
@RestController
@RequestMapping("api/v1/my")
@Api(value = "api/v1/my", tags = "我的")
public class MyController extends BaseController {

    @Inject
    private UserFavoriteService userFavoriteService;

    @ApiOperation(value = "我的收藏", notes = "我的收藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "文章标题或作者", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "pageNumber", value = "页码", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页多少条", paramType = "query", dataType = "int"),
    })
    @GetMapping("favorite")
    public ResData<PageResult> getFavorite(@RequestParam String keyword,
                                           @RequestParam(defaultValue = "0") Integer pageNumber,
                                           @RequestParam(defaultValue = "10") Integer pageSize) {
        PageResult pageResult = PageUtils.getPageResult(userFavoriteService.myFavorite(keyword, pageNumber, pageSize));
        return ResData.success(pageResult);
    }

    @ApiOperation(value = "取消收藏", notes = "取消收藏")
    @PostMapping("/remove_favorite/{id}")
    public synchronized ResData removeFavorite(@ApiParam(name = "id", value = "文章id")
                                               @PathVariable("id") Integer postsId) {
        userFavoriteService.removeFavorite(postsId);
        return ResData.success();
    }
}
