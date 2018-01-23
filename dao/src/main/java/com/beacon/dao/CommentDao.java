package com.beacon.dao;

import com.beacon.commons.base.BaseDao;
import com.beacon.entity.Comment;
import org.springframework.stereotype.Repository;

/**
 * 文章
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/23
 */
@Repository
public interface CommentDao extends BaseDao<Comment, Integer> {
}
