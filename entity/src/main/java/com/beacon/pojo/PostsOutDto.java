package com.beacon.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 文章编辑输出dto
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/22
 */
@ApiModel(value = "PostsOutDto", description = "文章编辑输出dto")
public class PostsOutDto {

    private Integer id;

    @ApiModelProperty(notes = "话题id")
    private Integer topicId;

    @ApiModelProperty(notes = "标题")
    private String title;

    @ApiModelProperty(notes = "状态，发布：published 未发布：unpublished", allowableValues = "published,unpublished")
    private String state;

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

    @ApiModelProperty(notes = "话题中的排序号")
    private int seqInTopic;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public int getSeqInTopic() {
        return seqInTopic;
    }

    public void setSeqInTopic(int seqInTopic) {
        this.seqInTopic = seqInTopic;
    }
}
