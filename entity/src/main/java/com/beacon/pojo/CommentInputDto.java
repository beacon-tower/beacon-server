package com.beacon.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 评论输入dto
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/23
 */
@ApiModel(value = "CommentInputDto", description = "评论输入dto")
public class CommentInputDto {

    @ApiModelProperty(value = "评论的父id")
    private Integer parentId;

    @ApiModelProperty(value = "评论的内容")
    private String content;

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
