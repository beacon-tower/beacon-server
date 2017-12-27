package com.beacon.dao;

import com.beacon.commons.base.BaseDao;
import com.beacon.entity.User;
import org.springframework.data.jpa.repository.Query;
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

    @Query(value = "SELECT u.* FROM user u WHERE u.mobile = ?1 or u.email = ?1", nativeQuery = true)
    User findByUsername(String username);

    User findByMobile(String mobile);
}
