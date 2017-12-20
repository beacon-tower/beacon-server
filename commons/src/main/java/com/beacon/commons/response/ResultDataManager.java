package com.beacon.commons.response;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 返回结果数据管理，data filter or handle
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/9/19
 */
public abstract class ResultDataManager {

    /**
     * 获取javaBean的名称
     *
     * @param bean javaBean
     * @return String
     */
    public static String getBeanName(Object bean) {
        if (bean == null) {
            return "dto";
        }
        String key = bean.getClass().getSimpleName();
        key = Character.toLowerCase(key.charAt(0))
                + key.substring(1, key.length());

        return key;
    }

    /**
     * 获取javaBean中对应字段的值
     *
     * @param bean
     * @param fields
     * @return
     */
    public static Map<String, Object> getValues(Object bean, String... fields) {
        if (bean == null) {
            return null;
        }
        if (fields == null || fields.length == 0) {
            return ReflectUtils.describe(bean, true);
        }

        Map<String, Object> map = new HashMap<>();
        for (String field : fields) {
            Object value = ReflectUtils.getValue(bean, field);
            map.put(field, value);
        }
        return map;
    }

    public static void initIntValue(Object bean) {
        if (bean == null) {
            return;
        }
        List<String> names = ReflectUtils.getFieldNames(bean.getClass());
        for (String name : names) {
            try {
                PropertyDescriptor pd = new PropertyDescriptor(name,
                        bean.getClass());
                Object invoke = pd.getReadMethod().invoke(bean);
                if (invoke != null) {
                    continue;
                }

                Class<?> returnType = pd.getReadMethod().getReturnType();
                if (returnType == Integer.class) {
                    ReflectUtils.setValue(bean, name, 0);
                }
                if (returnType == Double.class) {
                    ReflectUtils.setValue(bean, name, 0.0d);
                }
                if (returnType == Long.class) {
                    ReflectUtils.setValue(bean, name, 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Map<String, Object> getValuesExcludeFields(Object bean, String... excludeFields) {
        if (bean == null) {
            return null;
        }
        if (excludeFields == null || excludeFields.length == 0) {
            return ReflectUtils.describe(bean, true);
        }

        Map<String, Object> map = ReflectUtils.describe(bean, true);

        for (String field : excludeFields) {
            map.remove(field);
        }

        return map;
    }
}
