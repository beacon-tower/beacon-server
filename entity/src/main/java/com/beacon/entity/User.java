package com.beacon.entity;

import com.beacon.commons.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private int id;

    @ApiModelProperty(notes = "父id")
    private Integer parentId;

    @ApiModelProperty(notes = "登录用户名")
    @Column(nullable = false, length = 32)
    private String username;

    @ApiModelProperty(notes = "手机号")
    @Column(length = 20)
    private String mobile;

    @ApiModelProperty(notes = "登录密码")
    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @ApiModelProperty(notes = "真实姓名")
    @Column(length = 32)
    private String realName;

    @ApiModelProperty(notes = "身份证号")
    @Column(length = 18)
    private String cardNumber;

    @ApiModelProperty(notes = "昵称")
    @Column(length = 64)
    private String nickname;

    @ApiModelProperty(notes = "邮箱")
    @Column(length = 64)
    private String email;

    @ApiModelProperty(notes = "用户性别")
    @Column(length = 4)
    private String sex;

    @ApiModelProperty(notes = "头像id")
    @OneToOne
    @JoinColumn(name = "avatar_img_id")
    private Image avatarImage;

    @ApiModelProperty(notes = "注册来源方式（android,ios,pc,h5页面）", allowableValues = "android,ios,pc,h5")
    @Column(length = 32)
    private String registerSource;

    @ApiModelProperty(notes = "是否冻结（默认0：不冻结，1：冻结）")
    @Column(name = "locked")
    private Boolean locked = Boolean.FALSE;

    @ApiModelProperty(notes = "创建时间")
    @Column(nullable = false)
    private Date createTime = new Date();

    @ApiModelProperty(notes = "更新时间")
    private Date updateTime;
}
