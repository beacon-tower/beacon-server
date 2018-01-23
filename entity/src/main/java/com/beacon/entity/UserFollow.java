package com.beacon.entity;

import com.beacon.commons.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * 用户关注的作者
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "user_follow")
@ApiModel(value = "UserFollow", description = "用户关注的作者")
public class UserFollow extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ApiModelProperty(notes = "用户id")
    @Column(name = "user_id")
    private Integer userId;

    @ApiModelProperty(notes = "关注的用户id")
    private Integer followUserId;

}
