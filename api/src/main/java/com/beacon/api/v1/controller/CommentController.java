package com.beacon.api.v1.controller;

import com.beacon.commons.base.BaseController;
import com.beacon.commons.response.ResData;
import com.beacon.service.CommentService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * 评论
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/23
 */
@RestController
@RequestMapping("api/v1/comment")
@Api(value = "api/v1/comment", tags = "评论")
public class CommentController extends BaseController {

    @Inject
    private CommentService commentService;

    @ApiOperation(value = "点赞评论", notes = "文章详情页面，对评论点赞", response = ResData.class)
    @PostMapping("{id}/like")
    public ResData like(@ApiParam(name = "id", value = "评论id") @PathVariable Integer commentId) {
        commentService.like(commentId);
        return ResData.success();
    }
}
