package com.beacon.commons.response;

import com.beacon.commons.base.BaseResCode;
import com.beacon.commons.code.PublicResCode;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统一返回数据封装类
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/9/19
 */
public class ResData implements Serializable {

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误码描述
     */
    private String msg;

    /**
     * 返回数据
     */
    private Map<String, Object> data = new HashMap<>();

    public ResData(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResData(BaseResCode resCode) {
        this.code = resCode.getCode();
        this.msg = resCode.getMsg();
    }

    public ResData(BaseResCode resCode, String msg) {
        this.code = resCode.getCode();
        this.msg = msg;
    }

    public static ResData build(Boolean isSuccess) {
        return isSuccess ? new ResData(PublicResCode.SUCCESS) : new ResData(PublicResCode.SERVER_EXCEPTION);
    }

    public static ResData buildSuccess() {
        return new ResData(PublicResCode.SUCCESS);
    }

    public static ResData buildFailed(BaseResCode resCode) {
        return new ResData(resCode);
    }

    public static ResData buildFailed(BaseResCode resCode, String msg) {
        return new ResData(resCode.getCode(), msg);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public ResData putData(String key, Object value) {
        if (value instanceof Describle) {
            this.data.put(key, ((Describle) value).describle());
            return this;
        }
        this.data.put(key, value);
        return this;
    }

    public ResData putBeanDataAll(Object bean) {
        String key = ResultDataManager.getBeanName(bean);
        data.put(key, bean);
        return this;
    }

    /**
     * use the bean class simple name to key witch the first work is lower case
     *
     * @param bean   the field that return to app
     * @param fields 。。
     */
    public ResData putBeanData(Object bean, String... fields) {
        String key = ResultDataManager.getBeanName(bean);
        putBeanData(key, bean, Boolean.FALSE, fields);
        return this;
    }

    public ResData putBeanData(String key, Object bean, String... fields) {
        putBeanData(key, bean, Boolean.FALSE, fields);
        return this;
    }

    /**
     * use the user key to put bean <p>
     *
     * @param key    key in return json
     * @param bean   data
     * @param fields fields is bean data
     */
    public ResData putBeanData(String key, Object bean, Boolean isExclude, String... fields) {
        if (bean instanceof Map) {
            data.put(key, bean);
            return this;
        }

        if (fields == null || fields.length == 0) {
            ResultDataManager.initIntValue(bean);
            data.put(key, bean);
            return this;
        }

        Map<String, Object> values;
        if (isExclude) {
            values = ResultDataManager.getValuesExcludeFields(bean, fields);
        } else {
            values = ResultDataManager.getValues(bean, fields);
        }
        data.put(key, values);
        return this;
    }

    @SuppressWarnings("unchecked")
    public ResData putBeanFields(Object bean) {
        if (bean instanceof Map) {
            data.putAll((Map<? extends String, ?>) bean);
            return this;
        }
        for (Class c = bean.getClass(); c.getSuperclass() != null; c = c.getSuperclass()) {
            for (Field field : c.getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    if (!data.containsKey(field.getName())) {
                        data.put(field.getName(), field.get(bean));
                    }
                } catch (IllegalAccessException e) {

                }
            }
        }
        return this;
    }

    public ResData putListData(String key, List<?> listBean, String... fields) {
        putListData(key, listBean, Boolean.FALSE, fields);
        return this;
    }

    /**
     * put list into data use customer key
     *
     * @param key      out json key
     * @param listBean out json list bean
     * @param fields   out json bean fields
     * @see #(List, String...)
     */
    @SuppressWarnings("unchecked")
    public ResData putListData(String key, List<?> listBean, Boolean isExclude, String... fields) {

        List<Map<String, Object>> retData = new ArrayList<>();

        if (listBean == null) {
            data.put(key, new ArrayList<>());
            return this;
        }

        for (Object bean : listBean) {
            if (bean instanceof Map) {
                retData.add((Map<String, Object>) bean);
                continue;
            }

            if (bean instanceof Describle) {
                Map<String, Object> values = ((Describle) bean).describle();
                retData.add(values);
                continue;
            }
            Map<String, Object> values;
            if (isExclude) {
                values = ResultDataManager.getValuesExcludeFields(bean, fields);
            } else {
                values = ResultDataManager.getValues(bean, fields);
            }
            retData.add(values);
        }
        data.put(key, retData);
        return this;
    }

    @Override
    public String toString() {
        return "ResponseInfo{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
