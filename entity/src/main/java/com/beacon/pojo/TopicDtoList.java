package com.beacon.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 话题列表
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/22
 */
@ApiModel(value = "TopicDtoList", description = "话题列表")
public class TopicDtoList {

    @ApiModelProperty(value = "话题id")
    private Integer id;

    @ApiModelProperty(value = "话题名称")
    private String name;

    @ApiModelProperty(notes = "是否关注")
    private Boolean followed;

    @ApiModelProperty(notes = "关注数")
    private Integer followCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getFollowed() {
        return followed;
    }

    public void setFollowed(Boolean followed) {
        this.followed = followed;
    }

    public Integer getFollowCount() {
        return followCount;
    }

    public void setFollowCount(Integer followCount) {
        this.followCount = followCount;
    }
}
