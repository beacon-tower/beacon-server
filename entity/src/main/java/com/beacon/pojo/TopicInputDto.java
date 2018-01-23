package com.beacon.pojo;

import com.beacon.entity.Image;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 话题dto
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/16
 */
@ApiModel(value = "TopicInputDto", description = "话题dto")
public class TopicInputDto {

    @ApiModelProperty(notes = "名称")
    @NotNull
    private String name;

    @ApiModelProperty(notes = "图标")
    private Image iconImg;

    @ApiModelProperty(notes = "描述")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getIconImg() {
        return iconImg;
    }

    public void setIconImg(Image iconImg) {
        this.iconImg = iconImg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
