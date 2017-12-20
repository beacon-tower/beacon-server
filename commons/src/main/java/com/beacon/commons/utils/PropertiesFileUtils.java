package com.beacon.commons.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 资源文件读取工具
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/5/26
 */
public class PropertiesFileUtils {

    /**
     * 当打开多个资源文件时，缓存资源文件
     */
    private static HashMap<String, PropertiesFileUtils> CONFIG_MAP = new HashMap<>();
    /**
     * 打开文件时间，判断超时使用
     */
    private Date loadTime = null;
    /**
     * 资源文件
     */
    private ResourceBundle resourceBundle = null;
    /**
     * 默认资源文件名称
     */
    private static final String RESOURCE_NAME = "config";
    /**
     * 缓存时间
     */
    private static final Integer TIME_OUT = 60 * 1000;

    private PropertiesFileUtils(String name) {
        this.loadTime = new Date();
        this.resourceBundle = ResourceBundle.getBundle(name);
    }

    public static synchronized PropertiesFileUtils getInstance() {
        return getInstance(RESOURCE_NAME);
    }

    public static synchronized PropertiesFileUtils getInstance(String name) {
        PropertiesFileUtils conf = CONFIG_MAP.computeIfAbsent(name, k -> new PropertiesFileUtils(name));
        // 判断是否打开的资源文件是否超时1分钟
        if ((System.currentTimeMillis() - conf.getLoadTime().getTime()) > TIME_OUT) {
            conf = new PropertiesFileUtils(name);
            CONFIG_MAP.put(name, conf);
        }
        return conf;
    }

    /**
     * 根据key读取value
     *
     * @param key 键
     * @return 值
     */
    public String get(String key) {
        try {
            return resourceBundle.getString(key);
        } catch (MissingResourceException e) {
            return "";
        }
    }

    /**
     * 根据key读取value(整形)
     *
     * @param key 键
     * @return 值
     */
    public Integer getInt(String key) {
        try {
            String value = resourceBundle.getString(key);
            return Integer.parseInt(value);
        } catch (MissingResourceException e) {
            return null;
        }
    }

    /**
     * 根据key读取value(布尔)
     *
     * @param key 键
     * @return 值
     */
    public boolean getBool(String key) {
        try {
            String value = resourceBundle.getString(key);
            return "true".equals(value);
        } catch (MissingResourceException e) {
            return false;
        }
    }

    public Date getLoadTime() {
        return loadTime;
    }

}
