package com.beacon.enums.dict;

import com.beacon.commons.base.BaseDict;

import java.util.HashMap;
import java.util.Map;

/**
 * 邮件发送字典
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/10
 */
public enum EmailLogDict implements BaseDict {

    STATE_SUCCESS("success", "发送成功"),
    STATE_FAIL("fail", "发送失败"),
    ;

    private static Map<String, String> elements = new HashMap<>();

    static {
        EmailLogDict[] values = EmailLogDict.values();
        for (EmailLogDict dict : values) {
            elements.put(dict.key, dict.value);
        }
    }

    private final String key;
    private final String value;

    EmailLogDict(String key, String value) {
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
