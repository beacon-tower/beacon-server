package com.beacon.commons.base;

/**
 * 基类异常
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/9/19
 */
public class BaseException extends RuntimeException {

    private BaseResCode resCode;

    public void setResCode(BaseResCode resCode) {
        this.resCode = resCode;
    }

    public BaseResCode getResCode() {
        return resCode;
    }

    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
    }

}

