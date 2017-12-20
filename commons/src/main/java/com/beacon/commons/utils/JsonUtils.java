package com.beacon.commons.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Json转换操作帮助类
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/5/26
 */
public class JsonUtils {

    /**
     * 将java pojo对象转换为json字符串
     * 注：不要将该方法用于复杂对象，生成的字符串可能会很大，容易造成内存问题
     *
     * @param object pojo对象
     * @return json字符串
     */
    public static String objectToJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将json字符串转换为java pojo对象
     */
    public static <T> T jsonToObject(String json, Class<T> valueType) {
        return jsonToObject(json, valueType, false);
    }

    /**
     * 将json字符串转换为java pojo对象(忽略首字母大写)
     */
    public static <T> T jsonToObject(String json, Class<T> valueType, boolean uppercase) {
        ObjectMapper mapper = new ObjectMapper();

        if (uppercase) {
            mapper.setPropertyNamingStrategy(new PropertyNamingStrategy() {
                private static final long serialVersionUID = 1L;

                // 反序列化时调用p
                @Override
                public String nameForSetterMethod(MapperConfig<?> config,
                                                  AnnotatedMethod method, String defaultName) {
                    return method.getName().substring(3);
                }

                // 序列化时调用
                @Override
                public String nameForGetterMethod(MapperConfig<?> config,
                                                  AnnotatedMethod method, String defaultName) {
                    return method.getName().substring(3);
                }
            });
        }

        try {
            return mapper.readValue(json, valueType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将json字符串还原为数组对象
     *
     * @param json      json字符串
     * @param valueType 数组元素类型
     */
    public static <T> T[] jsonToArray(String json, Class<T> valueType) {
        ObjectMapper mapper = new ObjectMapper();
        TypeFactory typeFactory = TypeFactory.defaultInstance();
        ArrayType arrayType = typeFactory.constructArrayType(valueType);

        try {
            return mapper.readValue(json, arrayType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将json字符串还原为List对象，因为List是泛型，无法作为对象.class直接指定
     * 所以需要独立处理
     *
     * @param json      json字符串
     * @param valueType List元素类型
     */
    public static <T> List<T> jsonToList(String json, Class<T> valueType) {
        ObjectMapper mapper = new ObjectMapper();
        TypeFactory typeFactory = TypeFactory.defaultInstance();
        CollectionType collectionType = typeFactory.constructCollectionType(List.class, valueType);

        try {
            return mapper.readValue(json, collectionType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将json字符串还原为Set对象，因为Set是泛型，无法作为对象.class直接指定
     * 所以需要独立处理
     *
     * @param json      json字符串
     * @param valueType Set元素类型
     */
    public static <T> Set<T> jsonToSet(String json, Class<T> valueType) {
        ObjectMapper mapper = new ObjectMapper();
        TypeFactory typeFactory = TypeFactory.defaultInstance();
        CollectionType collectionType = typeFactory.constructCollectionType(Set.class, valueType);

        try {
            return mapper.readValue(json, collectionType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将json字符串还原为Map对象
     *
     * @param json      json字符串
     * @param valueType List元素类型
     */
    public static <K, V> Map<K, V> jsonToMap(String json, Class<K> keyType, Class<V> valueType) {
        ObjectMapper mapper = new ObjectMapper();
        TypeFactory typeFactory = TypeFactory.defaultInstance();
        MapType mapType = typeFactory.constructMapType(Map.class, keyType, valueType);

        try {
            return mapper.readValue(json, mapType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
