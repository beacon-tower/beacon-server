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

    USER_LOCK(10001, "账号已被锁定,请联系管理员!"),
    MOBILE_EXIST(10002, "手机号已被注册!"),
    MOBILE_FORMAT_ERROR(10003, "手机号格式有误!"),
    MOBILE_NOT_EXIST(10004, "手机号不存在!"),
    CODE_ERROR(10005, "手机验证码不正确!"),
    SECRET_ERROR(10006, "钱包密钥不正确，请检查后填写!"),
    USERNAME_ERROR(10008, "账号不存在!"),
    ADDRESS_ERROR(10009, "钱包地址有误!"),
    EMAIL_FORMAT_ERROR(10010, "邮箱格式有误!"),
    EMAIL_EXIST(10011, "邮箱已被使用!"),
    PUBLIC_KEY_ERROR(10012, "公钥参数错误!"),
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
