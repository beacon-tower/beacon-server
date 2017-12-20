package com.beacon.dao;

import com.beacon.commons.base.BaseDao;
import com.beacon.entity.User;
import org.springframework.stereotype.Repository;

/**
 * 用户
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/10/9
 */
@Repository
public interface UserDao extends BaseDao <User, Integer> {

    User findByUsername(String username);
}
