package com.beacon.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 话题列表
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/22
 */
@ApiModel(value = "TopicDtoList", description = "话题列表")
public class TopicDtoList {

    @ApiModelProperty(value = "话题id")
    private Integer id;

    @ApiModelProperty(value = "话题名称")
    private String name;

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

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
}
