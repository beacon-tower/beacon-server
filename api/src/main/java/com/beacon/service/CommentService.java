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
import com.beacon.utils.ShiroUtils;
import org.springframework.data.domain.Sort;
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
        boolean hasLike = userLikeService.hasLike(userId, String.valueOf(UserLikeDict.TARGET_TYPE_COMMENT), commentId);
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
}
