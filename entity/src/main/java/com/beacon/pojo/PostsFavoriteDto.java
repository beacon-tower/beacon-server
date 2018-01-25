package com.beacon.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 文章收藏展示dto
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/24
 */
@ApiModel(value = "PostsFavoriteDto", description = "文章收藏展示dto")
public class PostsFavoriteDto {

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
    private String shortContent;

    @ApiModelProperty(notes = "格式化的发表时间，例如：几分钟前")
    private String formatTime;

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

    public String getShortContent() {
        return shortContent;
    }

    public void setShortContent(String shortContent) {
        this.shortContent = shortContent;
    }

    public String getFormatTime() {
        return formatTime;
    }

    public void setFormatTime(String formatTime) {
        this.formatTime = formatTime;
    }
}
