package com.beacon.entity;

import com.beacon.commons.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * 用户关注的话题
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/16
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "user_topic")
@ApiModel(value = "UserTopic", description = "用户关注的话题表")
public class UserTopic extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ApiModelProperty(notes = "用户id")
    @Column(name = "user_id")
    private Integer userId;

    @ApiModelProperty(notes = "话题id")
    @Column(name = "topic_id")
    private Integer topicId;
}
