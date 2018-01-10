package com.beacon.enums.dict;

import com.beacon.commons.base.BaseDict;

import java.util.HashMap;
import java.util.Map;

/**
 * 发送短信记录字典
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/9/27
 */
public enum SmsLogDict implements BaseDict {

    TYPE_REGISTER("register", "尊敬的用户！您的验证码是%s。有效期为%s分钟，请及时验证！"),

    STATE_SUCCESS("success", "发送成功"),
    STATE_FAIL("fail", "发送失败"),
    ;

    private static Map<String, String> elements = new HashMap<>();

    static {
        SmsLogDict[] values = SmsLogDict.values();
        for (SmsLogDict smsLogDict : values) {
            elements.put(smsLogDict.key, smsLogDict.value);
        }
    }

    private final String key;
    private final String value;

    SmsLogDict(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return this.key;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public boolean compare(String key) {
        return this.toString().equals(key);
    }

    public static String getValue(String key) {
        return elements.get(key);
    }

}
