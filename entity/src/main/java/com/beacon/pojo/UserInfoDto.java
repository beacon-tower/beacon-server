package com.beacon.pojo;

import com.beacon.entity.Image;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 用户信息dto
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/9
 */
@ApiModel(value = "UserInfoDto", description = "用户信息dto")
public class UserInfoDto {

    @ApiModelProperty(notes = "主键")
    private Integer id;

    @ApiModelProperty(notes = "手机号", required = true)
    private String mobile;

    @ApiModelProperty(notes = "邮箱")
    private String email;

    @ApiModelProperty(notes = "昵称")
    private String nickname;

    @ApiModelProperty(notes = "头像")
    private Image avatarImage;

    @ApiModelProperty(notes = "用户性别")
    private String sex;

    @ApiModelProperty(notes = "生日")
    private Date birthday;

    @ApiModelProperty(notes = "个人简介")
    private String introduce;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Image getAvatarImage() {
        return avatarImage;
    }

    public void setAvatarImage(Image avatarImage) {
        this.avatarImage = avatarImage;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
