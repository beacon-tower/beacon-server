package com.beacon.dao;

import com.beacon.commons.base.BaseDao;
import com.beacon.entity.UserFollow;
import org.springframework.stereotype.Repository;

/**
 * 用户关注
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/23
 */
@Repository
public interface UserFollowDao extends BaseDao<UserFollow, Integer> {
    UserFollow findByUserIdAndFollowUserId(Integer userId, Integer followUserId);
}
