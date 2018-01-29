package com.beacon.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

/**
 * @author Ian.Su
 * @version $ Id PostsListOutDto.java, v 0.1 2018/1/26 11:47 Ian.Su Exp $
 **/
@Data
@ApiModel(value = "Posts", description = "文章列表")
public class PostsListOutDto {


    @ApiModelProperty(notes = "文章id")
    private Integer id;

    @ApiModelProperty(notes = "标题")
    private String title;

    @ApiModelProperty(notes = "内容")
    private String content;

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

    @ApiModelProperty(notes = "创建时间")
    private Date createTime = new Date();

    @ApiModelProperty(notes = "作者头像")
    private String url;

    @ApiModelProperty(notes = "作者被关注数")
    private Integer followCount;

    @ApiModelProperty(notes = "作者昵称")
    private String nickname;

    @ApiModelProperty("作者ID")
    private Long userId;

    @ApiModelProperty(notes = "话题id")
    private Integer topicId;

    @ApiModelProperty(notes = "话题名称")
    private String topicName;



}
