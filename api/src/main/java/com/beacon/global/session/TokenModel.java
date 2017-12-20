package com.beacon.global.session;

/**
 * Token的Model类，可以增加字段提高安全性，例如时间戳、url签名
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/12/13
 */
public class TokenModel {

    /**
     * token 类型
     */
    public static final String TYPE_API = "api";
    public static final String TYPE_BIZ = "biz";
    public static final String TYPE_SYS = "sys";

    //用户id
    private Integer userId;

    //随机生成的uuid
    private String token;

    //token类型，api、sys、biz
    private String type;

    public TokenModel(Integer userId, String token, String type) {
        this.userId = userId;
        this.token = token;
        this.type = type;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
