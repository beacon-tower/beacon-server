package com.beacon.dao;

import com.beacon.commons.base.BaseDao;
import com.beacon.entity.UserFavorite;
import org.springframework.stereotype.Repository;

/**
 * 用户收藏
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/23
 */
@Repository
public interface UserFavoriteDao extends BaseDao<UserFavorite, Integer> {
}
