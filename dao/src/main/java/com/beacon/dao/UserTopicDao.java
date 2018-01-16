package com.beacon.dao;

import com.beacon.commons.base.BaseDao;
import com.beacon.entity.UserTopic;
import org.springframework.stereotype.Repository;

/**
 * 用户话题
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/16
 */
@Repository
public interface UserTopicDao extends BaseDao<UserTopic, Integer> {
    UserTopic findByUserIdAndTopicId(Integer userId, Integer topicId);
}
