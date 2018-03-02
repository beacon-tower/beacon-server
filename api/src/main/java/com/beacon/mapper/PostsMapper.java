package com.beacon.mapper;

import com.beacon.entity.Posts;
import com.beacon.entity.Topic;
import com.beacon.entity.User;
import com.beacon.pojo.*;
import com.beacon.service.TopicService;
import com.beacon.service.UserFollowService;
import com.beacon.utils.RelativeDateUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * 文章
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/22
 */
@Mapper(componentModel = "jsr330")
public abstract class PostsMapper {

    @Inject
    private UserMapper userMapper;

    @Inject
    private UserFollowService userFollowService;

    @Inject
    private TopicService topicService;

    public abstract Posts fromDto(PostsInputDto postsInputDto);

    public abstract PostsOutDto toDto(Posts posts);

    public abstract List<PostsOutDto> toDtoList(List<Posts> postsList);

    public abstract PostsDetailDto toDetailDto(Posts posts);

    @Mappings({
            @Mapping(source = "content", target = "shortContent"),
            @Mapping(source = "createTime", target = "formatTime"),
            @Mapping(source = "user.id", target = "followed"),
            @Mapping(source = "topicId", target = "topicName"),
    })
    public abstract PostsFavoriteDto toFavoriteDto(Posts posts);

    public abstract List<PostsFavoriteDto> toFavoriteDtoList(List<Posts> postsList);

    public UserShowDto toUserShowDto(User user) {
        return userMapper.toShowDto(user);
    }

    public String toFormatTime(Date createTime) {
        return RelativeDateUtils.format(createTime);
    }

    public boolean setFollowed(Integer userId) {
        return userFollowService.hasFollowedAuthor(userId);
    }

    public String setTopicName(Integer topicId) {
        Topic topic = topicService.findById(topicId);
        if (topic == null) {
            return null;
        }
        return topic.getName();
    }
}
