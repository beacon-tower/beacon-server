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
    private int followCount;

    @ApiModelProperty(notes = "文章数")
    private int postsCount;

    @ApiModelProperty(notes = "是否关注")
    private boolean followed;

    @ApiModelProperty(value = "话题排序号")
    private Integer seq;

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

    public int getFollowCount() {
        return followCount;
    }

    public void setFollowCount(int followCount) {
        this.followCount = followCount;
    }

    public int getPostsCount() {
        return postsCount;
    }

    public void setPostsCount(int postsCount) {
        this.postsCount = postsCount;
    }

    public boolean isFollowed() {
        return followed;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
}
