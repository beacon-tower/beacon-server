package com.beacon.dao;

import com.beacon.commons.base.BaseDao;
import com.beacon.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/10/9
 */
@Repository
public interface UserDao extends BaseDao<User, Integer> {

    @Query(value = "SELECT u.* FROM user u WHERE u.mobile = ?1 or u.email = ?1", nativeQuery = true)
    User findByUsername(String username);

    User findByMobile(String mobile);

    User findByEmail(String email);

    @Query(value = "SELECT u.* FROM user u  ORDER BY u.follow_count DESC LIMIT ?1,?2", nativeQuery = true)
    List<User> findUsers(Integer start, Integer limit);


    @Query(value = "SELECT u.* FROM user u WHERE u.id not in ( SELECT follow_user_id FROM user_follow WHERE user_id=?1) ORDER BY u.follow_count DESC LIMIT ?2,?3", nativeQuery = true)
    List<User> findUsersNotFollow(Integer userId, Integer start, Integer limit);

}
