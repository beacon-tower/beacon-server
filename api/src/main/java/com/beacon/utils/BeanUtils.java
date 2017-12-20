package com.beacon.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Set;

/**
 * 类复制
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/12/20
 */
public class BeanUtils {

    /**
     * 获取bean中为null的属性
     *
     * @param source bean
     * @return 属性数组
     */
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * spring beanUtils的扩展方法，忽略null属性
     *
     * @param source 源
     * @param target 目标
     */
    public static void copyPropertiesIgnoreNull(Object source, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    /**
     * spring beanUtils的扩展方法，忽略null属性和其它的属性
     *
     * @param source 源
     * @param target 目标
     */
    public static void copyPropertiesIgnoreNull(Object source, Object target, String... ignoreProperties) {
        org.springframework.beans.BeanUtils.copyProperties(source, target,
                ArrayUtils.addAll(getNullPropertyNames(source), ignoreProperties));
    }
}
