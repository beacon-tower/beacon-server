package com.beacon.commons.exception;

import com.beacon.commons.base.BaseException;
import com.beacon.commons.base.BaseResCode;

/**
 * 业务异常
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/9/19
 */
public class ResException extends BaseException {

    public ResException(BaseResCode resCode, String msg) {
        super(msg);
        setResCode(resCode);
    }

    public ResException(BaseResCode resCode) {
        super(resCode.getMsg());
        setResCode(resCode);
    }
}
