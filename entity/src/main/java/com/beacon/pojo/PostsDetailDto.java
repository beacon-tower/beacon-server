package com.beacon.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 文章详情dto
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/23
 */
@ApiModel(value = "PostsDetailDto", description = "文章详情dto")
public class PostsDetailDto {

    private Integer id;

    @ApiModelProperty(notes = "作者")
    private UserShowDto user;

    @ApiModelProperty(notes = "标题")
    private String title;

    @ApiModelProperty(notes = "获得的烽火币")
    private double coinCount;

    @ApiModelProperty(notes = "阅读数")
    private int readCount;

    @ApiModelProperty(notes = "收藏数")
    private int favoriteCount;

    @ApiModelProperty(notes = "文字数")
    private int wordsCount;

    @ApiModelProperty(notes = "点赞数")
    private int likesCount;

    @ApiModelProperty(notes = "评论数")
    private int commentCount;

    @ApiModelProperty(notes = "文章内容")
    private String content;

    @ApiModelProperty(notes = "是否关注")
    private boolean followed;

    @ApiModelProperty(notes = "创建时间")
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserShowDto getUser() {
        return user;
    }

    public void setUser(UserShowDto user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getCoinCount() {
        return coinCount;
    }

    public void setCoinCount(double coinCount) {
        this.coinCount = coinCount;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public int getWordsCount() {
        return wordsCount;
    }

    public void setWordsCount(int wordsCount) {
        this.wordsCount = wordsCount;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isFollowed() {
        return followed;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
