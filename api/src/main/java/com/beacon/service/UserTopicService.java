package com.beacon.service;

import com.beacon.commons.base.BaseDao;
import com.beacon.commons.base.BaseService;
import com.beacon.dao.UserTopicDao;
import com.beacon.entity.Topic;
import com.beacon.entity.UserTopic;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;

/**
 * 用户话题
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/16
 */
@Service
public class UserTopicService extends BaseService<UserTopic, Integer> {

    @Inject
    private UserTopicDao userTopicDao;

    @Inject
    private TopicService topicService;

    @Override
    public BaseDao<UserTopic, Integer> getBaseDao() {
        return this.userTopicDao;
    }

    public void toggleFollow(Integer userId, Topic topic) {
        UserTopic userTopic = userTopicDao.findByUserIdAndTopicId(userId, topic.getId());
        int followNum = topic.getFollowCount();
        //取消话题关注
        if (userTopic != null) {
            super.delete(userTopic);
            followNum = followNum - 1;
        } else { //关注话题
            userTopic = new UserTopic();
            userTopic.setUserId(userId);
            userTopic.setTopicId(topic.getId());
            super.save(userTopic);
            followNum = followNum + 1;
        }
        topic.setFollowCount(followNum);
        topic.setUpdateTime(new Date());
        topicService.update(topic);
    }
}
