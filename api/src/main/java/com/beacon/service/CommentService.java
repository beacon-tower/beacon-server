package com.beacon.service;

import com.beacon.commons.base.BaseDao;
import com.beacon.commons.base.BaseService;
import com.beacon.commons.specification.SimpleSpecificationBuilder;
import com.beacon.commons.specification.SpecificationOperator;
import com.beacon.commons.utils.AssertUtils;
import com.beacon.commons.utils.CollectionUtils;
import com.beacon.dao.CommentDao;
import com.beacon.entity.Comment;
import com.beacon.entity.UserLike;
import com.beacon.enums.dict.UserLikeDict;
import com.beacon.mapper.CommentMapper;
import com.beacon.pojo.CommentOutDto;
import com.beacon.pojo.CommentParentOutDto;
import com.beacon.utils.ShiroUtils;
import com.google.common.collect.Lists;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

import static com.beacon.enums.code.PostsResCode.COMMENT_ID_ERROR;
import static com.beacon.enums.code.PostsResCode.COMMENT_REPEAT_LIKED;

/**
 * 评论
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/23
 */
@Service
public class CommentService extends BaseService<Comment, Integer> {

    @Inject
    private CommentDao commentDao;

    @Inject
    private UserLikeService userLikeService;

    @Inject
    private CommentMapper commentMapper;

    @Override
    public BaseDao<Comment, Integer> getBaseDao() {
        return this.commentDao;
    }

    /**
     * 获取评论楼数
     *
     * @param postsId 文章id
     * @return 最大楼数
     */
    public int getMaxFloor(Integer postsId) {
        SimpleSpecificationBuilder<Comment> spec = new SimpleSpecificationBuilder<>();
        spec.add("postsId", SpecificationOperator.Operator.eq, postsId);
        spec.add("parentId", SpecificationOperator.Operator.isNull, null);
        List<Comment> commentList = super.findList(spec.generateSpecification(), new Sort(Sort.Direction.DESC, "floor"));
        if (CollectionUtils.isEmpty(commentList)) {
            return 0;
        }
        return commentList.get(0).getFloor();
    }

    /**
     * 评论点赞
     *
     * @param commentId 评论id
     */
    public void like(Integer commentId) {
        Integer userId = ShiroUtils.getUserId();
        boolean hasLike = userLikeService.hasLike(String.valueOf(UserLikeDict.TARGET_TYPE_COMMENT), commentId);
        AssertUtils.isTrue(COMMENT_REPEAT_LIKED, !hasLike);

        Comment comment = super.findById(commentId);
        AssertUtils.notNull(COMMENT_ID_ERROR, comment);
        comment.setLikesCount(comment.getLikesCount() + 1);
        super.update(comment);

        UserLike userLike = new UserLike();
        userLike.setUserId(userId);
        userLike.setTargetType(String.valueOf(UserLikeDict.TARGET_TYPE_COMMENT));
        userLike.setTargetValue(commentId);
        userLikeService.save(userLike);
    }

    public List<CommentOutDto> getChildren(Integer commentId) {
        SimpleSpecificationBuilder<Comment> spec = new SimpleSpecificationBuilder<>();
        spec.add("parentId", SpecificationOperator.Operator.eq, commentId);
        List<Comment> commentList = super.findList(spec.generateSpecification(), new Sort(Sort.Direction.DESC, "id"));
        return commentMapper.toOutDtoList(commentList);
    }

    public CommentParentOutDto toParentOutDto(Comment comment) {
        CommentParentOutDto commentParentOutDto = commentMapper.toParentOutDto(comment);
        commentParentOutDto.setLiked(userLikeService.hasLike(String.valueOf(UserLikeDict.TARGET_TYPE_COMMENT), comment.getPostsId()));
        commentParentOutDto.setChildren(this.getChildren(comment.getId()));
        return commentParentOutDto;
    }

    public List<CommentParentOutDto> toParentOutDtoList(List<Comment> commentList) {
        if (CollectionUtils.isEmpty(commentList)) {
            return null;
        }

        List<CommentParentOutDto> list = Lists.newArrayListWithCapacity(commentList.size());
        for (Comment comment : commentList) {
            list.add(this.toParentOutDto(comment));
        }
        return list;
    }

    public Page<CommentParentOutDto> commentList(Integer postsId, Integer pageNumber, Integer pageSize) {
        SimpleSpecificationBuilder<Comment> spec = new SimpleSpecificationBuilder<>();
        spec.add("postsId", SpecificationOperator.Operator.eq, postsId);
        spec.add("parentId", SpecificationOperator.Operator.isNull, null);
        Pageable pageable = new PageRequest(pageNumber, pageSize, new Sort(Sort.Direction.DESC, "id"));
        Page<Comment> commentPage = super.findAll(spec.generateSpecification(), pageable);
        List<CommentParentOutDto> commentParentOutDtoList = this.toParentOutDtoList(commentPage.getContent());
        return commentPage.hasContent() ? new PageImpl<>(commentParentOutDtoList, pageable, commentPage.getTotalElements()) : null;
    }
}
