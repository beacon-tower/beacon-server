package com.beacon.commons.code;

import com.beacon.commons.base.BaseResCode;

/**
 * 公共异常码
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/9/19
 */
public enum PublicResCode implements BaseResCode {

    SUCCESS(200, "请求成功!"),
    USER_NO_LOGIN(401, "请先登录后再操作!"),
    NOT_AUTHORIZED(403, "请求的资源没有权限!"),
    SERVER_EXCEPTION(500, "服务器异常!"),
    OPERATE_FAIL(600, "操作失败!"),
    ASCH_CALL_FAIL(700, "asch call fail, {0}"),
    PARAMS_IS_NULL(1001, "{0}参数不能为空!"),
    PARAMS_EXCEPTION(1002, "{0}参数异常!"),;

    private final int code;
    private final String msg;

    PublicResCode(int code, String msg) {
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

    public static PublicResCode valueOf(int code) {
        PublicResCode[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            PublicResCode resCode = var1[var3];
            if (resCode.code == code) {
                return resCode;
            }
        }

        throw new IllegalArgumentException("No matching constant for [" + code + "]");
    }
}
