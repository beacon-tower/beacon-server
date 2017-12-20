package com.beacon.enums.code;

import com.beacon.commons.base.BaseResCode;

/**
 * 用户异常码
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/9/19
 */
public enum UserResCode implements BaseResCode {

    TOKEN_INVALID(10000, "Token已失效，请重新登录!"),
    USER_LOCK(10001, "账号已被锁定,请联系管理员!"),
    USERNAME_ERROR(10002, "账号不正确!"),
    PASSWORD_ERROR(10003, "密码不正确!"),
    MOBILE_ERROR(10004, "手机号不正确!"),
    MOBILE_EXIST(10005, "手机号已被注册!"),
    MOBILE_FORMAT_ERROR(10006, "手机号格式有误!"),
    MOBILE_NOT_EXIST(10007, "手机号不存在!"),
    CODE_ERROR(10008, "手机验证码不正确!"),
    PASSWORD_FORMAT_ERROR(10009, "密码格式有误!"),
    PASSWORD_NOT_SAME(10010, "两次密码输入不一致!"),
    ;

    private final int code;
    private final String msg;

    UserResCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return Integer.toString(this.code);
    }

    public static UserResCode valueOf(int code) {
        UserResCode[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            UserResCode resCode = var1[var3];
            if (resCode.code == code) {
                return resCode;
            }
        }

        throw new IllegalArgumentException("No matching constant for [" + code + "]");
    }
}
