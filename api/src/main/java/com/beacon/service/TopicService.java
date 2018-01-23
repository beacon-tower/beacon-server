package com.beacon.service;

import com.beacon.commons.base.BaseDao;
import com.beacon.commons.base.BaseService;
import com.beacon.dao.TopicDao;
import com.beacon.dao.UserTopicDao;
import com.beacon.entity.Topic;
import com.beacon.entity.UserTopic;
import com.beacon.mapper.TopicMapper;
import com.beacon.pojo.TopicDtoList;
import com.beacon.pojo.TopicInputDto;
import com.beacon.pojo.TopicOutputDto;
import com.beacon.utils.BeanUtils;
import org.springframework.stereotype.Service;
import org.testng.collections.Lists;

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

    @Inject
    private UserTopicDao userTopicDao;

    @Override
    public BaseDao<Topic, Integer> getBaseDao() {
        return this.topicDao;
    }

    public List<TopicDtoList> findList(Integer userId, Integer top) {
        if (top == null) {
            top = Integer.MAX_VALUE;
        }
        List<Object[]> list = topicDao.findListByUserId(userId, top);
        List<TopicDtoList> topicsDtoList = Lists.newArrayList(list.size());
        for (Object[] obj : list) {
            TopicDtoList topicDtoList = new TopicDtoList();
            topicDtoList.setId((Integer) obj[0]);
            topicDtoList.setName((String) obj[1]);
            topicDtoList.setSeq((Integer) obj[2]);
            topicsDtoList.add(topicDtoList);
        }
        return topicsDtoList;
    }

    public List<TopicOutputDto> findMoreList(Integer userId, Integer top) {
        if (top == null) {
            top = Integer.MAX_VALUE;
        }
        List<Topic> topicList = topicDao.findAllOrderByFollowNum(top);
        List<TopicOutputDto> topicOutputDtoList = Lists.newArrayList(topicList.size());
        topicList.forEach(topic -> {
            UserTopic userTopic = userTopicDao.findByUserIdAndTopicId(userId, topic.getId());
            TopicOutputDto topicOutputDto = new TopicOutputDto();
            BeanUtils.copyPropertiesIgnoreNull(topic, topicOutputDto, "iconImg");
            if (topic.getIconImg() != null) {
                topicOutputDto.setIconImg(topic.getIconImg().getUrl());
            }
            topicOutputDto.setFollowed(userTopic != null);
            topicOutputDtoList.add(topicOutputDto);
        });
        return topicOutputDtoList;
    }

    public void add(TopicInputDto topicInputDto) {
        Topic topic = topicMapper.fromDto(topicInputDto);
        super.save(topic);
    }
}
