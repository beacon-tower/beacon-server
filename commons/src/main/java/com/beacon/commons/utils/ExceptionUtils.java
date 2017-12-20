package com.beacon.commons.utils;

import com.beacon.commons.base.BaseResCode;
import com.beacon.commons.exception.ResException;
import org.springframework.util.CollectionUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

/**
 * 异常工具类
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/9/19
 */
public abstract class ExceptionUtils {

    public static void throwResponseException(BaseResCode resCode, String... var1) throws ResException {
        throw new ResException(resCode, getMsg(resCode, var1));
    }

    private static String getMsg(BaseResCode resCode, String... var1) {
        String msg = resCode.getMsg();
        if (!CollectionUtils.isEmpty(Arrays.asList(var1))) {
            for (int i = 0; i < var1.length; i++) {
                msg = msg.replace("{" + i + "}", var1[i]);
            }
        } else {
            msg = msg.replace("{0}", "");
        }
        return msg;
    }

    public static String getTrace(Throwable t) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer = stringWriter.getBuffer();
        return buffer.toString();
    }

}
