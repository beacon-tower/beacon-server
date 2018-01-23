package com.beacon.mapper;

import com.beacon.entity.Comment;
import com.beacon.entity.User;
import com.beacon.pojo.CommentOutDto;
import com.beacon.pojo.CommentParentOutDto;
import com.beacon.pojo.UserShowDto;
import org.mapstruct.Mapper;

import javax.inject.Inject;
import java.util.List;

/**
 * 评论dto转换
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/10/10
 */
@Mapper(componentModel = "jsr330")
public abstract class CommentMapper {

    @Inject
    private UserMapper userMapper;

    public abstract CommentOutDto toOutDto(Comment comment);

    public abstract List<CommentOutDto> toOutDtoList(List<Comment> commentList);

    public abstract CommentParentOutDto toParentOutDto(Comment comment);

    public UserShowDto toUserShowDto(User user) {
        return userMapper.toShowDto(user);
    }
}
