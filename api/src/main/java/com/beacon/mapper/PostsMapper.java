package com.beacon.mapper;

import com.beacon.entity.Posts;
import com.beacon.entity.User;
import com.beacon.pojo.PostsDetailDto;
import com.beacon.pojo.PostsInputDto;
import com.beacon.pojo.PostsOutDto;
import com.beacon.pojo.UserShowDto;
import org.mapstruct.Mapper;

import javax.inject.Inject;
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

    public abstract Posts fromDto(PostsInputDto postsInputDto);

    public abstract PostsOutDto toDto(Posts posts);

    public abstract List<PostsOutDto> toDtoList(List<Posts> postsList);

    public abstract PostsDetailDto toDetailDto(Posts posts);

    public UserShowDto toUserShowDto(User user) {
        return userMapper.toShowDto(user);
    }
}
