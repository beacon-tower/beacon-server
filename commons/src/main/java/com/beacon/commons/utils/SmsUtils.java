package com.beacon.commons.utils;

import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 发送短信帮助类,使用云片
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/9/19
 */
public class SmsUtils {

    private static final Logger log = LoggerFactory.getLogger(SmsUtils.class);

    private static YunpianClient client;

    private static final String API_KEY = "8c247cb4b69255c4c2233f4f575f4295";

    static {
        client = (new YunpianClient(API_KEY)).init();
    }

    public static Result<?> send(String phone, String content) {
        Map<String, String> param = client.newParam(2);
        param.put("mobile", phone);
        param.put("text", content);
        return client.sms().single_send(param);
    }

    public static Result<?> batchSend(String phone, String content) {
        Map<String, String> param = client.newParam(2);
        param.put("mobile", phone);
        param.put("text", content);
        return client.sms().batch_send(param);
    }

}
