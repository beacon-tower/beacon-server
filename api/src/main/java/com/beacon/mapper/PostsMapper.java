package com.beacon.mapper;

import com.beacon.entity.Posts;
import com.beacon.pojo.PostsDetailDto;
import com.beacon.pojo.PostsInputDto;
import com.beacon.pojo.PostsOutDto;
import org.mapstruct.Mapper;

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

    public abstract Posts fromDto(PostsInputDto postsInputDto);

    public abstract PostsOutDto toDto(Posts posts);

    public abstract List<PostsOutDto> toDtoList(List<Posts> postsList);

    public abstract PostsDetailDto toDetailDto(Posts posts);
}
