package com.beacon.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户钱包
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/2/27
 */
@Data
@ApiModel(value = "UserWalletDto", description = "用户钱包")
public class UserWalletDto {

    @ApiModelProperty(notes = "主键")
    private Integer id;

    @ApiModelProperty(notes = "烽火币")
    private String balance;

    @ApiModelProperty(notes = "钱包地址")
    private String walletAddress;

    @ApiModelProperty(notes = "历史赚取数")
    private String historyBalance;

}
