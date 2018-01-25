package com.beacon.api.v1.controller;

import com.beacon.commons.base.BaseController;
import com.beacon.commons.response.ResData;
import com.beacon.commons.utils.AssertUtils;
import com.beacon.pojo.CommentInputDto;
import com.beacon.pojo.CommentOutDto;
import com.beacon.pojo.CommentParentOutDto;
import com.beacon.pojo.PageResult;
import com.beacon.service.PostsService;
import com.beacon.service.UserFollowService;
import com.beacon.utils.PageUtils;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

import static com.beacon.commons.code.PublicResCode.PARAMS_IS_NULL;

/**
 * 文章
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/23
 */
@RestController
@RequestMapping("api/v1/posts")
@Api(value = "api/v1/posts", tags = "文章")
public class PostsController extends BaseController {

    @Inject
    private PostsService postsService;

    @Inject
    private UserFollowService userFollowService;

    @ApiOperation(value = "文章详情", notes = "文章详情页面内容", response = ResData.class)
    @PostMapping("{id}")
    public ResData detail(@ApiParam(name = "id", value = "文章id")
                          @PathVariable("id") Integer postsId) {
        postsService.detail(postsId);
        return ResData.success();
    }

    @ApiOperation(value = "关注文章作者", notes = "文章详情页面，关注文章作者", response = ResData.class)
    @PostMapping("/toggle_follow_author/{id}")
    public synchronized ResData toggleFollowAuthor(@ApiParam(name = "id", value = "作者id")
                                                   @PathVariable("id") Integer followUserId) {
        userFollowService.toggleFollowAuthor(followUserId);
        return ResData.success();
    }

    @ApiOperation(value = "收藏文章", notes = "文章详情页面，对文章收藏", response = ResData.class)
    @PostMapping("{id}/favorite")
    public synchronized ResData addFavorite(@ApiParam(name = "id", value = "文章id")
                                            @PathVariable("id") Integer postsId) {
        postsService.addFavorite(postsId);
        return ResData.success();
    }

    @ApiOperation(value = "点赞文章", notes = "文章详情页面，对文章点赞", response = ResData.class)
    @PostMapping("{id}/like")
    public synchronized ResData like(@ApiParam(name = "id", value = "文章id")
                                     @PathVariable("id") Integer postsId) {
        postsService.like(postsId);
        return ResData.success();
    }

    @ApiOperation(value = "文章评论", notes = "文章详情页面，文章评论", response = CommentParentOutDto.class)
    @PostMapping("{id}/comment")
    public ResData<CommentOutDto> addComment(@ApiParam(name = "id", value = "文章id")
                                             @PathVariable("id") Integer postsId,
                                             @ApiParam(name = "commentInputDto", value = "评论对象")
                                             @RequestBody CommentInputDto commentInputDto) {
        String[] checkFields = new String[]{"comment"};
        AssertUtils.notNull(PARAMS_IS_NULL, checkFields, commentInputDto.getContent());

        CommentOutDto commentOutDto = postsService.addComment(postsId, commentInputDto);
        return ResData.success(commentOutDto);
    }

    @ApiOperation(value = "文章评论", notes = "文章详情页面，文章评论列表", response = PageResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", value = "页码", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页多少条", paramType = "query", dataType = "int"),
    })
    @GetMapping("{id}/comments")
    public ResData<PageResult> commentList(@ApiParam(name = "id", value = "文章id")
                                           @PathVariable("id") Integer postsId,
                                           @RequestParam(defaultValue = "0") Integer pageNumber,
                                           @RequestParam(defaultValue = "10") Integer pageSize) {

        PageResult pageResult = PageUtils.getPageResult(postsService.commentList(postsId, pageNumber, pageSize));
        return ResData.success(pageResult);
    }
}
