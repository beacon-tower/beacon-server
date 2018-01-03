package com.beacon.commons.response;

import com.beacon.commons.base.BaseResCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import static com.beacon.commons.code.PublicResCode.SERVER_EXCEPTION;
import static com.beacon.commons.code.PublicResCode.SUCCESS;

/**
 * 统一返回数据封装类
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/9/19
 */
@SuppressWarnings("unchecked")
@ApiModel(value = "ResData", description = "统一返回对象")
public class ResData<T> implements Serializable {

    /**
     * 错误码
     */
    @ApiModelProperty(notes = "返回码")
    private Integer code;

    /**
     * 错误码描述
     */
    @ApiModelProperty(notes = "返回信息说明")
    private String msg;

    /**
     * 返回数据
     */
    @ApiModelProperty(notes = "返回数据")
    private T data;

    private ResData() {

    }

    private ResData(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private ResData(BaseResCode resCode) {
        this(resCode.getCode(), resCode.getMsg(), null);
    }

    private ResData(BaseResCode resCode, T data) {
        this(resCode.getCode(), resCode.getMsg(), data);
    }

    private ResData(BaseResCode resCode, String msg, T data) {
        this(resCode.getCode(), msg, data);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public ResData<T> setData(T data) {
        this.data = data;
        return this;
    }

    public static <T> ResData<T> build(Boolean isSuccess) {
        return isSuccess ? success(null) : error(SERVER_EXCEPTION, null);
    }

    public static <T> ResData<T> success() {
        return new ResData(SUCCESS, null);
    }

    public static <T> ResData<T> success(T data) {
        return new ResData(SUCCESS, data);
    }

    public static <T> ResData<T> error(BaseResCode resCode) {
        return new ResData(resCode, null);
    }

    public static <T> ResData<T> error(BaseResCode resCode, T data) {
        return new ResData(resCode, data);
    }

    public static <T> ResData<T> error(BaseResCode resCode, String msg) {
        return new ResData(resCode, msg, null);
    }

    public static <T> ResData<T> error(BaseResCode resCode, String msg, T data) {
        return new ResData(resCode, msg, data);
    }

    @Override
    public String toString() {
        return "ResponseInfo{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
