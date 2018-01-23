package com.beacon.api.v1.controller;

import com.beacon.commons.base.BaseController;
import com.beacon.commons.response.ResData;
import com.beacon.commons.utils.AssertUtils;
import com.beacon.commons.utils.CollectionUtils;
import com.beacon.entity.Topic;
import com.beacon.pojo.PostsInputDto;
import com.beacon.pojo.PostsOutDto;
import com.beacon.pojo.TopicDtoList;
import com.beacon.service.PostsService;
import com.beacon.service.TopicService;
import com.beacon.utils.ShiroUtils;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

import static com.beacon.commons.code.PublicResCode.PARAMS_EXCEPTION;
import static com.beacon.commons.code.PublicResCode.PARAMS_IS_NULL;
import static com.beacon.enums.code.PostsResCode.TOPIC_ID_ERROR;

/**
 * 作者文章
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/22
 */
@RestController
@RequestMapping("api/v1/author")
@Api(value = "api/v1/author", tags = "作者写文章页")
public class AuthorController extends BaseController {

    @Inject
    private PostsService postsService;

    @Inject
    private TopicService topicService;

    @ApiOperation(value = "话题列表", notes = "作者进入写文章页，初始化页面调用此接口获取话题列表", response = TopicDtoList.class, responseContainer = "List")
    @GetMapping("topics")
    public ResData<List<TopicDtoList>> getTopics() {
        Integer userId = ShiroUtils.getUserId();
        List<TopicDtoList> topics = topicService.findList(userId, null);
        return ResData.success(topics);
    }

    @ApiOperation(value = "文章列表", notes = "作者进入写文章页，初始化页面调用此接口获取话题下的文章列表", response = PostsOutDto.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "话题id", required = true, paramType = "path", dataType = "int"),
    })
    @GetMapping("topics/{id}/posts")
    public ResData<List<PostsOutDto>> getPostsInTopics(@PathVariable("id") Integer topicId) {
        Integer userId = ShiroUtils.getUserId();
        List<PostsOutDto> postsOutDtoList = postsService.findAuthorList(userId, topicId);
        return ResData.success(postsOutDtoList);
    }

    @ApiOperation(value = "文章内容", notes = "获取单篇文章的内容", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "文章id", required = true, paramType = "path", dataType = "int"),
    })
    @GetMapping("posts/{id}")
    public ResData<String> getPostsContent(@PathVariable("id") Integer postsId) {
        String content = postsService.findContent(postsId);
        return ResData.success(content);
    }

    @ApiOperation(value = "保存文章", notes = "用于新建文章/编辑已有文章的保存", response = PostsOutDto.class)
    @PostMapping("posts")
    public ResData<PostsOutDto> postsSave(@ApiParam(name = "postInputDto", value = "文章dto")
                                          @RequestBody PostsInputDto postInputDto) {
        String[] checkFields = new String[]{"topicId", "title", "content"};
        AssertUtils.notNull(PARAMS_IS_NULL, checkFields,
                postInputDto.getTopicId(), postInputDto.getTitle(), postInputDto.getContent());
        Topic topic = topicService.findById(postInputDto.getTopicId());
        AssertUtils.notNull(TOPIC_ID_ERROR, topic);

        PostsOutDto postsOutDto = postsService.saveOrUpdate(postInputDto);

        return ResData.success(postsOutDto);
    }

    @ApiOperation(value = "发布文章", notes = "对已有的文章进行发布，发布后别人可见", response = ResData.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "文章id", required = true, paramType = "path", dataType = "int"),
    })
    @PostMapping("posts/{id}/publicize")
    public ResData<PostsOutDto> postsPublicize(@PathVariable("id") Integer postsId) {

        postsService.publicize(postsId);
        return ResData.success();
    }

    @ApiOperation(value = "移动顺序", notes = "同一话题内，移动顺序", response = ResData.class)
    @PutMapping("posts/sequence")
    public ResData<PostsOutDto> postsSequence(@ApiParam(name = "postsIdList", value = "调整顺序后的文章id列表")
                                              @RequestBody List<Integer> postsIdList) {
        AssertUtils.isTrue(PARAMS_EXCEPTION, !CollectionUtils.isEmpty(postsIdList));

        postsService.sequence(postsIdList);
        return ResData.success();
    }

    @ApiOperation(value = "移动顺序", notes = "移动到别的话题内，默认排在最前面", response = ResData.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "文章id", required = true, paramType = "path", dataType = "int"),
    })
    @PostMapping("posts/{id}/move")
    public ResData<PostsOutDto> postsMove(@PathVariable("id") Integer postsId,
                                          @ApiParam(name = "topicId", value = "移动到的话题id")
                                          @RequestBody Integer topicId) {

        Topic topic = topicService.findById(topicId);
        AssertUtils.notNull(TOPIC_ID_ERROR, topic);

        postsService.move(postsId, topicId);
        return ResData.success();
    }

    @ApiOperation(value = "删除文章", notes = "物理删除,后期可以设置为逻辑删除，可设置保留时间", response = ResData.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "文章id", required = true, paramType = "path", dataType = "int"),
    })
    @DeleteMapping("posts/{id}/destroy")
    public ResData<PostsOutDto> postsDestroy(@PathVariable("id") Integer postsId) {
        postsService.destroy(postsId);
        return ResData.success();
    }
}
