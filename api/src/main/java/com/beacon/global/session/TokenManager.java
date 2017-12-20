package com.beacon.global.session;

import java.util.Map;

/**
 * 对token进行操作的接口
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/12/13
 */
public interface TokenManager {

    /**
     * 创建一个token关联上指定用户
     *
     * @param userId 指定用户的id
     * @param type   指定用户的类型
     * @return 生成的auth token
     */
    String createToken(Integer userId, String type);

    /**
     * 刷新用户token
     *
     * @param userId 指定用户的id
     * @param type   指定用户的类型
     * @return 刷新后的token
     */
    String refreshToken(Integer userId, String type);

    /**
     * 构建token map
     *
     * @param token token
     * @return token map
     */
    Map<String, Object> buildTokenMap(String token, String type);

    /**
     * 检查token是否有效
     *
     * @param type      指定用户的类型
     * @param authToken 需要检测的token
     * @return 是否有效
     */
    boolean checkToken(String type, String authToken);

    /**
     * 通过token获取用户id
     *
     * @param type      指定用户的类型
     * @param authToken token
     * @return 用户id
     */
    Integer getUserIdByToken(String type, String authToken);

    /**
     * 清除token
     *
     * @param userId 登录用户的id
     * @param type   指定用户的类型
     */
    void delToken(Integer userId, String type);
}
