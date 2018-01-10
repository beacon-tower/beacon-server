package com.beacon.entity;

import com.beacon.commons.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

/**
 * 短信发送记录
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/9/30
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "sms_log")
@ApiModel(value = "SmsLog", description = "短信发送记录")
public class SmsLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @ApiModelProperty(notes = "接收手机号")
    @Column(nullable = false, length = 20)
    private String mobile;

    @ApiModelProperty(notes = "短信内容")
    @Column(nullable = false)
    private String content;

    @ApiModelProperty(notes = "业务类型", allowableValues = "register,login,forgetPwd")
    @Column(nullable = false, length = 32)
    private String type;

    @ApiModelProperty(notes = "发送状态", allowableValues = "success, fail")
    @Column(length = 10)
    private String state;

    @ApiModelProperty(notes = "返回结果信息")
    private String backResult;

    @ApiModelProperty(notes = "创建时间")
    @Column(nullable = false)
    private Date createTime = new Date();
}
