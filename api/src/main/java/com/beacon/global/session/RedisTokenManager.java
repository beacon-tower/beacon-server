package com.beacon.global.session;

import com.beacon.commons.utils.EncryptUtils;
import com.beacon.commons.utils.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.beacon.global.session.TokenModel.*;

/**
 * 通过redis存储和验证token
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/12/13
 */
@Component
public class RedisTokenManager implements TokenManager {

    //30天有效
    private static final long APP_EXPIRE = 30;
    //24小时有效
    private static final long SYSTEM_EXPIRE = 24;

    private static final String SEPARATOR_HR = "_";
    private static final String SEPARATOR_POINT = ".";

    @Inject
    private RedisTemplate redisTemplate;

    @Override
    @SuppressWarnings("unchecked")
    public String createToken(Integer userId, String type) {
        //生成一个token
        String token = TokenGenerator.generateValue();
        String key = type + SEPARATOR_HR + userId;
        String authToken = EncryptUtils.base64Encode(key.getBytes()) + SEPARATOR_POINT + token;
        this.setToken(type, key, authToken);
        return authToken;
    }

    @Override
    @SuppressWarnings("unchecked")
    public String refreshToken(Integer userId, String type) {
        String key = type + SEPARATOR_HR + userId;
        String authToken = (String) redisTemplate.boundValueOps(key).get();
        if (StringUtils.isNotEmpty(authToken)) {
            this.setExpire(type, key);
            return authToken;
        }
        return createToken(userId, type);
    }

    @Override
    public Map<String, Object> buildTokenMap(String token, String type) {
        long expire = 0;
        switch (type) {
            case TYPE_API:
                expire = APP_EXPIRE * 24 * 3600;
                break;
            case TYPE_BIZ:
            case TYPE_SYS:
                expire = SYSTEM_EXPIRE * 3600;
                break;
            default:

        }
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("expire", expire);
        return map;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean checkToken(String type, String authToken) {
        if (StringUtils.isEmpty(authToken) || !authToken.contains(SEPARATOR_POINT)) {
            return false;
        }
        String[] authTokenArray = authToken.split("\\.");
        String key = new String(EncryptUtils.base64Decode(authTokenArray[0]));
        String cacheToken = (String) redisTemplate.boundValueOps(key).get();
        if (StringUtils.isEmpty(cacheToken)) {
            return false;
        }
        if (authToken.equals(cacheToken) && type.equals(key.split(SEPARATOR_HR)[0])) {
            this.setExpire(type, key);
            return true;
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Integer getUserIdByToken(String type, String authToken) {
        if (StringUtils.isNotEmpty(authToken) && authToken.contains(SEPARATOR_POINT)) {
            String[] authTokenArray = authToken.split("\\.");
            String key = new String(EncryptUtils.base64Decode(authTokenArray[0]));
            String cacheToken = (String) redisTemplate.boundValueOps(key).get();
            if (StringUtils.isNotEmpty(cacheToken) && authToken.equals(cacheToken)
                    && type.equals(key.split(SEPARATOR_HR)[0])) {
                return Integer.valueOf(key.split(SEPARATOR_HR)[1]);
            }
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void delToken(Integer userId, String type) {
        String key = type + SEPARATOR_HR + userId;
        redisTemplate.delete(key);
    }

    @SuppressWarnings("unchecked")
    private void setToken(String type, String key, String authToken) {
        switch (type) {
            case TYPE_API:
                redisTemplate.boundValueOps(key).set(authToken, APP_EXPIRE, TimeUnit.DAYS);
                break;
            case TYPE_BIZ:
                redisTemplate.boundValueOps(key).set(authToken, SYSTEM_EXPIRE, TimeUnit.HOURS);
                break;
            case TYPE_SYS:
                redisTemplate.boundValueOps(key).set(authToken, SYSTEM_EXPIRE, TimeUnit.HOURS);
                break;
            default:

        }
    }

    @SuppressWarnings("unchecked")
    private void setExpire(String type, String key) {
        //如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
        switch (type) {
            case TYPE_API:
                redisTemplate.boundValueOps(key).expire(APP_EXPIRE, TimeUnit.DAYS);
                break;
            case TYPE_BIZ:
                redisTemplate.boundValueOps(key).expire(SYSTEM_EXPIRE, TimeUnit.HOURS);
                break;
            case TYPE_SYS:
                redisTemplate.boundValueOps(key).expire(SYSTEM_EXPIRE, TimeUnit.HOURS);
                break;
            default:

        }
    }

}
