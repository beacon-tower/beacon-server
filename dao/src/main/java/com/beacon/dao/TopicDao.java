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

    @Query(value = "SELECT t.name FROM topic t\n" +
            "WHERE t.id IN (\n" +
            "  SELECT a.id FROM topic a LEFT JOIN user_topic ut ON a.id = ut.topic_id WHERE ut.user_id = ?1\n" +
            "  UNION " +
            "  SELECT b.id FROM topic b" +
            ")\n" +
            "ORDER BY t.follow_num DESC LIMIT 0, ?2", nativeQuery = true)
    List<String> findNameListByUserId(Integer userId, Integer top);

    @Query(value = "SELECT t.* FROM topic t ORDER BY t.follow_num DESC limit 0, ?1", nativeQuery = true)
    List<Topic> findAllOrderByFollowNum(Integer top);
}
