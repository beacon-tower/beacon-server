package com.beacon.mapper;

import com.beacon.entity.Topic;
import com.beacon.pojo.TopicInputDto;
import com.beacon.pojo.TopicOutputDto;
import org.mapstruct.Mapper;

/**
 * 用户dto转换
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/10/10
 */
@Mapper(componentModel = "jsr330")
public abstract class TopicMapper {

    public abstract Topic fromDto(TopicInputDto topicInputDto);

    public abstract TopicOutputDto toDto(Topic topic);

}
