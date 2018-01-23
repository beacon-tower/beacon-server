package com.beacon.mapper;

import com.beacon.entity.Comment;
import com.beacon.pojo.CommentOutDto;
import com.beacon.pojo.CommentParentOutDto;
import org.mapstruct.Mapper;

/**
 * 评论dto转换
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/10/10
 */
@Mapper(componentModel = "jsr330")
public abstract class CommentMapper {

    public abstract CommentOutDto toOutDto(Comment comment);

    public abstract CommentParentOutDto toParentOutDto(Comment comment);
}
