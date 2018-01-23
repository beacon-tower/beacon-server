package com.beacon.enums.code;

import com.beacon.commons.base.BaseResCode;

/**
 * 用户异常码
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/9/19
 */
public enum PostsResCode implements BaseResCode {

    TOPIC_ID_ERROR(20001, "请选择一个正确的话题!"),
    POSTS_ID_ERROR(20002, "编辑的文章不存在!"),
    POSTS_REPEAT_LIKED(20003, "文章不能重复点赞!"),
    POSTS_REPEAT_FAVORITE(20004, "文章不能重复收藏!"),
    COMMENT_ID_ERROR(20005, "评论不存在!"),
    COMMENT_REPEAT_LIKED(20006, "评论不能重复点赞!"),
    ;

    private final int code;
    private final String msg;

    PostsResCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return Integer.toString(this.code);
    }

    public static PostsResCode valueOf(int code) {
        PostsResCode[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            PostsResCode resCode = var1[var3];
            if (resCode.code == code) {
                return resCode;
            }
        }

        throw new IllegalArgumentException("No matching constant for [" + code + "]");
    }
}
