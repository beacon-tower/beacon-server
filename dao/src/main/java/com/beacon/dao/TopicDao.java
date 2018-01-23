package com.beacon.dao;

import com.beacon.commons.base.BaseDao;
import com.beacon.entity.Topic;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 话题
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/16
 */
@Repository
public interface TopicDao extends BaseDao<Topic, Integer> {

    @Query(value = "SELECT t.id id, t.name name, ut.seq seq FROM topic t LEFT JOIN user_topic ut ON t.id = ut.topic_id WHERE ut.user_id = ?1\n" +
            "ORDER BY ut.seq ASC, t.follow_num DESC LIMIT 0, ?2", nativeQuery = true)
    List<Object[]> findListByUserId(Integer userId, Integer top);

    @Query(value = "SELECT t.* FROM topic t ORDER BY t.follow_num DESC limit 0, ?1", nativeQuery = true)
    List<Topic> findAllOrderByFollowNum(Integer top);
}
