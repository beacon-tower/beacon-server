package com.beacon.enums.dict;

import com.beacon.commons.base.BaseDict;

/**
 * app用户字典
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/9/27
 */
public enum PostsDict implements BaseDict {

    STATE_PUBLISHED("published", "文章发布"),
    STATE_UNPUBLISHED("unpublished", "文章未发布"),
    ;

    private final String key;
    private final String value;

    PostsDict(String key, String value) {
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
