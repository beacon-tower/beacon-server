package com.beacon.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 话题dto
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/16
 */
@ApiModel(value = "TopicOutputDto", description = "话题dto")
public class TopicOutputDto {

    private Integer id;

    @ApiModelProperty(notes = "名称")
    private String name;

    @ApiModelProperty(notes = "图标")
    private String iconImg;

    @ApiModelProperty(notes = "描述")
    private String description;

    @ApiModelProperty(notes = "关注数")
    private int followNum;

    @ApiModelProperty(notes = "文章数")
    private int articleNum;

    @ApiModelProperty(notes = "是否关注")
    private boolean followStatus;

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

    public String getIconImg() {
        return iconImg;
    }

    public void setIconImg(String iconImg) {
        this.iconImg = iconImg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFollowNum() {
        return followNum;
    }

    public void setFollowNum(int followNum) {
        this.followNum = followNum;
    }

    public int getArticleNum() {
        return articleNum;
    }

    public void setArticleNum(int articleNum) {
        this.articleNum = articleNum;
    }

    public boolean isFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(boolean followStatus) {
        this.followStatus = followStatus;
    }
}
