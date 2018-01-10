package com.beacon.commons.base;

/**
 * 全局字典
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/9/27
 */
public interface BaseDict {

    String getKey();

    String getValue();

    boolean compare(String key);

    String toString();
}
