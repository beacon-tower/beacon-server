package com.beacon.entity;

import com.beacon.commons.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户表
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/9/30
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "user")
@ApiModel(value = "User", description = "用户表")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ApiModelProperty(notes = "钱包地址")
    @Column(nullable = false, length = 64)
    private String walletAddress;

    @ApiModelProperty(notes = "手机号")
    @Column(length = 11)
    private String mobile;

    @ApiModelProperty(notes = "邮箱")
    @Column(length = 64)
    private String email;

    @ApiModelProperty(notes = "昵称")
    @Column(length = 64)
    private String nickname;

    @ApiModelProperty(notes = "头像id")
    @OneToOne
    @JoinColumn(name = "avatar_img_id")
    private Image avatarImg;

    @ApiModelProperty(notes = "用户性别")
    @Column(length = 4)
    private String sex;

    @ApiModelProperty(notes = "生日")
    private Date birthday;

    @ApiModelProperty(notes = "个人简介")
    private String introduce;

    @ApiModelProperty(notes = "被关注数")
    private int followCount;

    @ApiModelProperty(notes = "文章字数")
    private int wordsCount;

    @ApiModelProperty(notes = "创建时间")
    @Column(nullable = false)
    private Date createTime = new Date();

    @ApiModelProperty(notes = "更新时间")
    private Date updateTime;
}
