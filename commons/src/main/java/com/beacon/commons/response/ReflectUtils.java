package com.beacon.commons.response;

import org.springframework.beans.BeanUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 反射工具
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/9/19
 */
public abstract class ReflectUtils {

    public static Method getReadMethod(Class beanClass, String fieldName) {
        PropertyDescriptor pd;
        try {
            pd = new PropertyDescriptor(fieldName, beanClass);
            return pd.getReadMethod();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Method getWriteMethod(Class beanClass, String fieldName) {
        PropertyDescriptor pd;
        try {
            pd = new PropertyDescriptor(fieldName, beanClass);
            return pd.getWriteMethod();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setValue(Object bean, String field, Object value) {
        try {
            Method m = getWriteMethod(bean.getClass(), field);
            if (m == null) {
                return;
            }
            Class<?>[] pts = m.getParameterTypes();
            if (pts == null || pts.length != 1) {
                return;
            }
            BeanUtils.copyProperties(bean, value, field);
        } catch (Exception e) {
            throw new RuntimeException("set value exception!");
        }
    }

    /**
     * get value from bean witch the field name is given<p>
     * notice that this method would not return null if the field is number<p>
     *
     * @param source
     * @param fieldName
     * @return
     */
    public static Object getValue(Object source, String fieldName) {
        return getValue(source, fieldName, false);
    }

    /**
     * return value from source <p>
     * if needNull is true this method will return null number<p>
     *
     * @param source
     * @param fieldName
     * @param needNull
     * @return
     */
    public static Object getValue(Object source, String fieldName, boolean needNull) {
        if (source == null || fieldName == null) {
            return null;
        }
        try {
            PropertyDescriptor pd = new PropertyDescriptor(fieldName,
                    source.getClass());
            Object invoke = pd.getReadMethod().invoke(source);
            if (invoke != null || needNull) {
                return invoke;
            }

            Class<?> returnType = pd.getReadMethod().getReturnType();
            if (returnType == Integer.class) {
                return 0;
            }
            if (returnType == Double.class) {
                return 0.0d;
            }
            if (returnType == Long.class) {
                return 0.0d;
            }

            return invoke;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> getFieldNames(Class beanClass) {
        if (beanClass == null) {
            return null;
        }

        BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(beanClass, Object.class);
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();

            List<String> names = new ArrayList<>();
            for (PropertyDescriptor pd : pds) {
                String name = pd.getName();
                names.add(name);
            }
            return names;
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Map<String, Object> describe(Object bean, boolean needNull) {
        if (bean == null) {
            return null;
        }

        Map<String, Object> des = new HashMap<>();

        List<String> names = getFieldNames(bean.getClass());
        for (String name : names) {
            Object value = getValue(bean, name);
            if (value != null || needNull) {
                des.put(name, value);
            }
        }
        return des;
    }

    public static Map<String, Object> describe(Object bean) {
        return describe(bean, false);
    }

}
