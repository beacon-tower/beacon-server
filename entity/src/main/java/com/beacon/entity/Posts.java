package com.beacon.entity;

import com.beacon.commons.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

/**
 * 文章
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/19
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "posts")
@ApiModel(value = "Posts", description = "文章表")
public class Posts extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ApiModelProperty(notes = "用户")
    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @ApiModelProperty(notes = "话题id")
    private Integer topicId;

    @ApiModelProperty(notes = "标题")
    @Column(name = "title", length = 32)
    private String title;

    @ApiModelProperty(notes = "内容")
    @Lob
    @Basic
    private String content;

    @ApiModelProperty(notes = "状态，发布：published 未发布：unpublished", allowableValues = "published,unpublished")
    @Column(length = 32)
    private String state;

    @ApiModelProperty(notes = "创建时间")
    @Column(nullable = false)
    private Date createTime = new Date();

    @ApiModelProperty(notes = "更新时间")
    private Date updateTime;
}
