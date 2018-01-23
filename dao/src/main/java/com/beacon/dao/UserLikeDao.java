package com.beacon.dao;

import com.beacon.commons.base.BaseDao;
import com.beacon.entity.UserLike;
import org.springframework.stereotype.Repository;

/**
 * 用户点赞
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/23
 */
@Repository
public interface UserLikeDao extends BaseDao<UserLike, Integer> {
}
