package com.beacon.enums.dict;

import com.beacon.commons.base.BaseDict;

/**
 * 用户点赞字典
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/9/27
 */
public enum UserLikeDict implements BaseDict {

    TARGET_TYPE_POSTS("posts", "文章点赞"),
    TARGET_TYPE_COMMENT("comment", "评论点赞"),
    ;

    private final String key;
    private final String value;

    UserLikeDict(String key, String value) {
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
