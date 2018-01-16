package com.beacon.service;

import com.beacon.commons.base.BaseDao;
import com.beacon.commons.base.BaseService;
import com.beacon.dao.TopicDao;
import com.beacon.entity.Topic;
import com.beacon.mapper.TopicMapper;
import com.beacon.pojo.TopicInputDto;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * 话题
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/16
 */
@Service
public class TopicService extends BaseService<Topic, Integer> {

    @Inject
    private TopicDao topicDao;

    @Inject
    private TopicMapper topicMapper;

    @Override
    public BaseDao<Topic, Integer> getBaseDao() {
        return this.topicDao;
    }

    public List<String> findListByUserId(Integer userId, Integer limit) {
        return topicDao.findListByUserId(userId, limit);
    }

    public void add(TopicInputDto topicInputDto) {
        Topic topic = topicMapper.fromDto(topicInputDto);
        super.save(topic);
    }
}
