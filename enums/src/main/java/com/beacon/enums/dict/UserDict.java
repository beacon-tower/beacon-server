package com.beacon.enums.dict;

import com.beacon.commons.base.BaseDict;

/**
 * app用户字典
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/9/27
 */
public enum UserDict implements BaseDict {

    REGISTER_SOURCE_ANDROID("android", "安卓"),
    REGISTER_SOURCE_IOS("ios", "IOS"),
    REGISTER_SOURCE_PC("pc", "PC"),
    REGISTER_SOURCE_H5("h5", "H5"),
    ;

    private final String key;
    private final String value;

    UserDict(String key, String value) {
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

}
