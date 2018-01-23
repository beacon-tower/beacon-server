package com.beacon.entity;

import com.beacon.commons.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * 用户收藏
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "user_favorite")
@ApiModel(value = "UserFavorite", description = "用户收藏")
public class UserFavorite extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ApiModelProperty(notes = "用户id")
    @Column(name = "user_id")
    private Integer userId;

    @ApiModelProperty(notes = "文章id")
    private Integer postsId;

}
