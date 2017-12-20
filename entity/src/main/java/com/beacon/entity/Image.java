package com.beacon.entity;

import com.beacon.commons.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

/**
 * 系统图片库
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/9/30
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "image", indexes = {@Index(name = "image_sort_index", columnList = "sort_index")})
@ApiModel(value = "Image", description = "系统图片库")
public class Image extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @ApiModelProperty(notes = "图片url")
    private String url;

    @ApiModelProperty(notes = "图片link")
    private String link;

    @ApiModelProperty(notes = "图片类型")
    @Column(length = 32)
    private String type;

    @ApiModelProperty(notes = "排序")
    @Column(name = "sort_index")
    private Integer sortIndex;

    @ApiModelProperty(notes = "是否删除（默认否，定期同步删除阿里云的图片）")
    private Boolean deleted = Boolean.FALSE;

    @ApiModelProperty(notes = "创建时间")
    @Column(nullable = false)
    private Date createTime = new Date();
}
