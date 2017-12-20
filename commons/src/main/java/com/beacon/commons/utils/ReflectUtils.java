package com.beacon.commons.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * 反射操作帮助类
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/9/19
 */
public class ReflectUtils {

    private static Set<String> fieldTypes = new HashSet<>();

    static {
        String[] types = {
                "byte", "Byte", "boolean", "Boolean", "char", "character", "short", "Short",
                "int", "Integer", "float", "Float", "long", "Long", "double", "Double", "String", "Date"};
        fieldTypes.addAll(Arrays.asList(types));
    }

    public static void setFieldValue(Object object, String fieldName, Object fieldValue) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            if (field == null) {
                return;
            }

            field.setAccessible(true);
            field.set(object, fieldValue);

//            PropertyDescriptor descriptor = new PropertyDescriptor(fieldName, object.getClass());
//            Method method = descriptor.getWriteMethod();
//            method.invoke(object, fieldValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * notice that this method would not return null if the field is number<p>
     *
     * @param object
     * @param fieldName
     * @return fieldValue
     */
    public static Object getFieldValue(Object object, String fieldName) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);

//            PropertyDescriptor descriptor = new PropertyDescriptor(fieldName, object.getClass());
//            Method method = descriptor.getReadMethod();
//            return method.invoke(object);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Map<String, Object> objectToMap(Object object, String... ignoreFields) {
        return objectToMap(object, false, ignoreFields);
    }

    public static Map<String, Object> objectToMap(Object object, boolean ignoreNull, String... ignoreFields) {
        Map<String, Object> result = new HashMap<>();
        if (object == null) {
            return result;
        }
        try {
            Set<String> ignoreFieldSet = new HashSet<>();
            if (ignoreFields != null) {
                for (String ignoreField : ignoreFields) {
                    ignoreFieldSet.add(ignoreField);
                }
            }

            Class c = object.getClass();
            for (; c != Object.class; c = c.getSuperclass()) {
                try {
                    for (Field field : c.getDeclaredFields()) {
                        if (ignoreFieldSet.contains(field.getName())
                                || !Modifier.toString(field.getModifiers()).equals("private")) {
                            continue;
                        }

                        field.setAccessible(true);
                        Object value = field.get(object);
                        if (value == null && ignoreNull) {
                            continue;
                        }

                        String type = field.getType().getSimpleName();
                        if (value != null && !fieldTypes.contains(type)) {
                            if (value instanceof Collection) {
                                List<Map<String, Object>> list = new ArrayList<>();
                                Collection collections = (Collection) value;
                                collections.forEach(collection -> list.add(objectToMap(collection, ignoreNull)));
                                result.put(field.getName(), list);
                            } else {
                                result.put(field.getName(), objectToMap(value, ignoreNull));
                            }
                        } else {
                            result.put(field.getName(), value);
                        }
                    }
                } catch (Exception e) {
                    //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                    //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
