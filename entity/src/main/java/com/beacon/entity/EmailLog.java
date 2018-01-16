package com.beacon.entity;

import com.beacon.commons.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

/**
 * 邮箱发送记录
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/10
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "email_log")
@ApiModel(value = "EmailLog", description = "邮箱发送记录")
public class EmailLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ApiModelProperty(notes = "业务类型", allowableValues = "bind")
    @Column(nullable = false, length = 32)
    private String type;

    @ApiModelProperty(notes = "发送人邮箱")
    @Column(nullable = false, length = 64)
    private String sendEmail;

    @ApiModelProperty(notes = "接收人邮箱")
    @Column(nullable = false, length = 64)
    private String receiveEmail;

    @ApiModelProperty(notes = "邮件标题")
    @Column(nullable = false)
    private String title;

    @ApiModelProperty(notes = "邮件内容")
    @Column(nullable = false)
    private String content;

    @ApiModelProperty(notes = "发送状态", allowableValues = "success, fail")
    @Column(length = 10)
    private String state;

    @ApiModelProperty(notes = "返回结果信息")
    private String backResult;

    @ApiModelProperty(notes = "创建时间")
    @Column(nullable = false)
    private Date createTime = new Date();

}
