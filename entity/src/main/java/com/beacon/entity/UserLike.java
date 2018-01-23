package com.beacon.entity;

import com.beacon.commons.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * 用户点赞
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "user_like")
@ApiModel(value = "UserLike", description = "用户点赞")
public class UserLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ApiModelProperty(notes = "用户id")
    @Column(name = "user_id")
    private Integer userId;

    @ApiModelProperty(notes = "目标类型,posts:文章 comment:评论", allowableValues = "posts,comment")
    private String targetType;

    @ApiModelProperty(notes = "目标值，文章id/评论id")
    private Integer targetValue;

}
