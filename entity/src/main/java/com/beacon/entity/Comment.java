package com.beacon.entity;

import com.beacon.commons.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

/**
 * 文章评论
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "comment")
@ApiModel(value = "Comment", description = "文章评论")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ApiModelProperty(value = "父id")
    private Integer parentId;

    @ApiModelProperty(value = "用户")
    private User user;

    @ApiModelProperty(value = "文章id")
    private Integer postsId;

    @ApiModelProperty(value = "几楼")
    private Integer floor;

    @ApiModelProperty(value = "子评论数")
    private int childrenCount;

    @ApiModelProperty(value = "评论内容")
    @Lob
    @Basic
    private String content;

    @ApiModelProperty(value = "点赞数")
    private int likesCount;

    @ApiModelProperty(value = "创建时间")
    private Date createTime = new Date();

}
