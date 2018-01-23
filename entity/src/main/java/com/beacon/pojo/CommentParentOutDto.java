package com.beacon.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 评论输出dto
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/23
 */
@ApiModel(value = "CommentParentOutDto", description = "父评论输出dto")
public class CommentParentOutDto extends CommentOutDto {

    @ApiModelProperty(value = "子评论列表")
    private List<CommentOutDto> children;

    @ApiModelProperty(value = "子评论数")
    private int childrenCount;

    @ApiModelProperty(value = "几楼")
    private Integer floor;

    @ApiModelProperty(value = "点赞数")
    private int likesCount;

    @ApiModelProperty(value = "是否点赞")
    private boolean liked;

    public List<CommentOutDto> getChildren() {
        return children;
    }

    public void setChildren(List<CommentOutDto> children) {
        this.children = children;
    }

    public int getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(int childrenCount) {
        this.childrenCount = childrenCount;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
