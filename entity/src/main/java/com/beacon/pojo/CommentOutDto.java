package com.beacon.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 评论输出dto
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/23
 */
@ApiModel(value = "CommentInputDto", description = "评论输出dto")
public class CommentOutDto {

    private Integer id;

    @ApiModelProperty(value = "父id")
    private Integer parentId;

    @ApiModelProperty(value = "用户")
    private UserShowDto user;

    @ApiModelProperty(value = "文章id")
    private Integer postsId;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "创建时间")
    private Date createTime = new Date();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public UserShowDto getUser() {
        return user;
    }

    public void setUser(UserShowDto user) {
        this.user = user;
    }

    public Integer getPostsId() {
        return postsId;
    }

    public void setPostsId(Integer postsId) {
        this.postsId = postsId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
