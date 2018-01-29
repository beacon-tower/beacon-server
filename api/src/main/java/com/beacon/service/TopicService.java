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
import java.util.ArrayList;
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

    public List<TopicOutputDto> findList(Integer top) {
        if (top == null) {
            top = Integer.MAX_VALUE;
        }
        List<Topic> list = topicDao.findAllOrderByFollowNum(top);
        List<TopicOutputDto> outList = new ArrayList<>(list.size());
        list.forEach(topic -> {
            TopicOutputDto dto = new TopicOutputDto();
            dto.setId(topic.getId());
            dto.setFollowCount(topic.getFollowCount());
            dto.setName(topic.getName());
            outList.add(dto);
        });

        return outList;
    }


    public List<TopicDtoList> findList(Integer userId, Integer top) {
        if (top == null) {
            top = Integer.MAX_VALUE;
        }
        List<Topic> topicList = topicDao.findAllOrderByFollowNum(top);
        List<TopicDtoList> topicsDtoList = Lists.newArrayList(topicList.size());
        topicList.forEach(topic -> {
            TopicDtoList topicDtoList = new TopicDtoList();
            topicDtoList.setId(topic.getId());
            topicDtoList.setName(topic.getName());
            topicDtoList.setFollowCount(topic.getFollowCount());
            if (userId != null) {
                UserTopic userTopic = userTopicDao.findByUserIdAndTopicId(userId, topic.getId());
                topicDtoList.setFollowed(userTopic != null);
            } else {
                topicDtoList.setFollowed(Boolean.FALSE);
            }
            topicsDtoList.add(topicDtoList);
        });

        topicsDtoList.sort((o1, o2) -> {
            int r = o2.getFollowed().compareTo(o1.getFollowed());
            if(r == 0){
                return o2.getFollowCount().compareTo(o1.getFollowCount());
            }
            return r;
        });
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

        topicOutputDtoList.sort((o1, o2) -> {
            int r = o2.getFollowed().compareTo(o1.getFollowed());
            if(r == 0){
                return o2.getFollowCount().compareTo(o1.getFollowCount());
            }
            return r;
        });

        return topicOutputDtoList;
    }

    public void add(TopicInputDto topicInputDto) {
        Topic topic = topicMapper.fromDto(topicInputDto);
        super.save(topic);
    }
}
