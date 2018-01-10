package com.beacon.global.constant;

/**
 * 全局常量
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/10/30
 */
public class Constant {

    /**
     * token授权标识
     */
    public static final String AUTHORIZATION = "token";

    /**
     * 当前所在的环境
     */
    public static final String PROFILE_ACTIVE_DEV = "dev";
    public static final String PROFILE_ACTIVE_TEST = "test";
    public static final String PROFILE_ACTIVE_PROD = "prod";

    /**
     * 超级管理员
     */
    public static final String ADMIN = "admin";

    /**
     * 注册手机号
     */
    public static final String REGISTER_MOBILE = "beacon:cache:register:mobile:";

    /**
     * 手机验证码
     */
    public static final String MOBILE_CODE = "beacon:cache:sms:mobile_code:";
}
