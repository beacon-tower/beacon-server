package com.beacon.commons.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis cache 工具类
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/8/30
 */
@Component
@SuppressWarnings("unchecked")
public class RedisHelper {

    private static final Logger log = LoggerFactory.getLogger(RedisHelper.class);

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 批量删除对应的value
     *
     * @param keys 存储的keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern 存储的key
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 删除对应的value
     *
     * @param key 存储的key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key 存储的key
     * @return boolean
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     *
     * @param key 存储的key
     * @return Object
     */
    public Object get(final String key) {
        try {
            DataType type = redisTemplate.type(key);
            if (DataType.NONE == type) {
                return null;
            } else if (DataType.STRING == type) {
                return redisTemplate.opsForValue().get(key);
            } else if (DataType.LIST == type) {
                return redisTemplate.opsForList().range(key, 0, -1);
            } else if (DataType.ZSET == type) {
                return redisTemplate.opsForZSet().range(key, 0, -1);
            } else if (DataType.HASH == type) {
                return redisTemplate.opsForHash().entries(key);
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("from redis get error = {} ", e.getMessage());
            return null;
        }
    }

    /**
     * 写入缓存
     *
     * @param key   存储的key
     * @param value 存储的值
     * @return boolean
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 写入缓存
     *
     * @param key   存储的key
     * @param value 存储的值
     * @return boolean
     */
    public boolean setExpire(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存键的有效期
     *
     * @param key        存储的key
     * @param expireTime 存储的时间
     * @return boolean
     */
    public boolean setExpireAt(final String key, Object value, Date expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expireAt(key, expireTime);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入list
     *
     * @param key   存储的key
     * @param value 存储的值
     * @return boolean
     */
    public boolean setList(final String key, Object value) {
        boolean result = false;
        try {
            ListOperations listOperations = redisTemplate.opsForList();
            listOperations.leftPushAll(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 实现redis 队列，FIFO从左边放入第一个元素
     *
     * @param key   存储的key
     * @param value 存储的值
     * @return boolean
     */
    public boolean leftPush(final String key, Object value) {
        try {
            ListOperations listOperations = redisTemplate.opsForList();
            listOperations.leftPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 实现redis 队列，FIFO从右边弹出第一个元素
     *
     * @param key 存储的key
     * @return 对象
     */
    public Object rightPop(final String key) {
        try {
            ListOperations listOperations = redisTemplate.opsForList();
            return listOperations.rightPop(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 移除队列中的值
     *
     * @param key   存储的key
     * @param value 移除的值
     * @return 条数
     */
    public Long remQueue(final String key, Object value) {
        try {
            ListOperations listOperations = redisTemplate.opsForList();
            return listOperations.remove(key, 0, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }


    /**
     * 队列个数
     *
     * @param key key
     * @return 数量
     */
    public Long queueCount(final String key) {
        try {
            ListOperations listOperations = redisTemplate.opsForList();
            return listOperations.size(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

}
