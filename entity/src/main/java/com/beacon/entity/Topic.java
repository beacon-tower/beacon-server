package com.beacon.entity;

import com.beacon.commons.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

/**
 * 话题
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/16
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "topic")
@ApiModel(value = "Topic", description = "话题表")
public class Topic extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ApiModelProperty(notes = "名称")
    @Column(name = "name", length = 16, nullable = false)
    private String name;

    @ApiModelProperty(notes = "图标")
    @Column(name = "icon_img_id")
    private Image iconImg;

    @ApiModelProperty(notes = "描述")
    private String description;

    @ApiModelProperty(notes = "关注数")
    private int followCount;

    @ApiModelProperty(notes = "文章数")
    private int postsCount;

    @ApiModelProperty(notes = "创建时间")
    @Column(nullable = false)
    private Date createTime = new Date();

    @ApiModelProperty(notes = "更新时间")
    private Date updateTime;
}
