package com.beacon.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 文章保存/编辑dto
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/22
 */
@ApiModel(value = "PostInputDto", description = "文章保存/编辑dto")
public class PostsInputDto {

    private Integer id;

    @ApiModelProperty(notes = "话题id")
    private Integer topicId;

    @ApiModelProperty(notes = "标题")
    private String title;

    @ApiModelProperty(notes = "内容")
    private String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
