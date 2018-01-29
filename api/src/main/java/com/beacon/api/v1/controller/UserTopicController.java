package com.beacon.api.v1.controller;

import com.beacon.commons.base.BaseController;
import com.beacon.commons.response.ResData;
import com.beacon.commons.utils.AssertUtils;
import com.beacon.entity.Topic;
import com.beacon.service.TopicService;
import com.beacon.service.UserTopicService;
import com.beacon.utils.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

import static com.beacon.commons.code.PublicResCode.PARAMS_EXCEPTION;

/**
 * 用户话题
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/10/10
 */
@RestController
@RequestMapping("api/v1/user/topic")
@Api(value = "api/v1/user/topic", tags = "用户话题")
public class UserTopicController extends BaseController {

    @Inject
    private UserTopicService userTopicService;

    @Inject
    private TopicService topicService;

    @ApiOperation(value = "话题开关", notes = "用户对喜欢的话题进行关注或者取消关注")
    @PostMapping("{id}/toggle_follow")
    public ResData toggleFollow(@ApiParam(name = "id", value = "被关注的话题id") @PathVariable Integer id) {
        Integer userId = ShiroUtils.getUserId();
        Topic topic = topicService.findById(id);
        AssertUtils.notNull(PARAMS_EXCEPTION, topic);

        userTopicService.toggleFollow(userId, topic);
        return ResData.success();
    }
}
