package com.beacon.commons.utils;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class StringUtils {

    public static Character[] chars = new Character[]{'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
            't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
            'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V'};

    public static Character[] numbers = new Character[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        }
        return isEmpty(String.valueOf(object));
    }

    /**
     * 判断字符串是否为空
     * 包括是否为null，是否为空字符串，过滤完空格后是否为空字符串
     *
     * @param str 待判定字符串
     * @return 是返回true，反之返回false
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.equals("null")) {
            return true;
        }
        return str.trim().isEmpty();
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 根据指定分隔符转换为List
     *
     * @param str   待转换字符串
     * @param regex 分隔符
     * @param trim  是否去除前后空格
     * @return 返回List
     */
    public static List<String> toList(String str, String regex, boolean trim) {
        List<String> list = new ArrayList<>();
        for (String item : str.split(regex)) {
            if (trim) {
                item = item.trim();
            }

            if (!item.isEmpty()) {
                list.add(item);
            }
        }
        return list;
    }

    /**
     * 根据指定分隔符转换为List
     * 分割时会去除前后空格
     */
    public static List<String> toList(String str, String regex) {
        return toList(str, regex, true);
    }

    /**
     * 根据指定分隔符转换为Map
     *
     * @param str       待转换字符串
     * @param itemRegex 分隔符
     * @param kvRegex   键值对分隔符
     * @return 返回Map
     */
    public static Map<String, String> toMap(String str, String itemRegex, String kvRegex) {
        String[] items = str.split(itemRegex);
        Map<String, String> map = new HashMap<>(items.length);
        for (String item : items) {
            String[] keyValue = item.split(kvRegex);
            if (keyValue.length == 1) {
                map.put(keyValue[0], "");
            } else if (keyValue.length >= 2) {
                map.put(keyValue[0].trim(), keyValue[1].trim());
            }
        }
        return map;
    }

    /**
     * 根据指定日期格式转换为Date
     *
     * @param str     待转换字符串
     * @param pattern 日期格式，SimpleDateFormat能够识别的格式
     * @return 成功返回Date，失败返回null
     */
    public static Date toDate(String str, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            return simpleDateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据yyyy-MM-dd的日期格式转换为Date
     */
    public static Date toDate(String str) {
        return toDate(str, "yyyy-MM-dd");
    }

    /**
     * 小数转百分数
     */
    public static String toPercent(String number) {
        if (isValidNumber(number)) {
            return toPercent(Double.valueOf(number));
        }

        return "";
    }

    public static String toPercent(double number) {
        return String.format("%.2f", Math.round(number * 10000) / 100.f);
    }

    /**
     * 判断字符串是否是数字
     *
     * @param input 字符串
     */
    public static boolean isValidNumber(String input) {
        try {
            new BigDecimal(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是整形数字
     *
     * @param input 字符串
     */
    public static boolean isValidInteger(String input) {
        try {
            Integer.valueOf(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是长整型数字
     *
     * @param input 字符串
     */
    public static boolean isValidLong(String input) {
        try {
            Long.valueOf(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是浮点型数字
     *
     * @param input 字符串
     */
    public static boolean isValidFloat(String input) {
        try {
            Float.valueOf(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是浮点型数字
     *
     * @param input 字符串
     */
    public static boolean isValidDouble(String input) {
        try {
            Double.valueOf(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是日期（yyyy-MM-dd）
     *
     * @param input 字符串
     */
    public static boolean isValidDate(String input) {
        return toDate(input) != null;
    }

    /**
     * 路径统一格式化
     */
    public static String formatPath(String path) {
        return path.replace("\\", File.separator);
    }

    /**
     * 半角转换为全角
     * <p>
     * 全角空格为12288，半角空格为32
     * 其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
     * 33-47    标点
     * 48-57    0~9
     * 58-64    标点
     * 65-90    A~Z
     * 91-96    标点
     * 97-122    a~z
     * 123-126  标点
     */
    public static String toSBC(String input) {
        if (input == null) {
            return null;
        }

        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == 32) {
                // 如果是半角空格，直接用全角空格替代
                chars[i] = 12288;
            } else if ((chars[i] >= 33) && (chars[i] <= 126)) {
                // 字符是!到~之间的可见字符
                chars[i] = (char) (chars[i] + 65248);
            }
        }
        return new String(chars);
    }

    /**
     * 全角转换为半角
     */
    public static String toDBC(String input) {
        if (input == null) {
            return null;
        }

        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == 12288) {
                // 如果是半角空格，直接用全角空格替代
                chars[i] = 32;
            } else if ((chars[i] >= 65281) && (chars[i] <= 65374)) {
                // 字符是!到~之间的可见字符
                chars[i] = (char) (chars[i] - 65248);
            }
        }
        return new String(chars);
    }

    /**
     * 随机字符串
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 随机字符串
     */
    public static String getUUID(int length) {
        Random random = new Random();
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < length; i++) {
            buffer.append(chars[random.nextInt(chars.length)]);
        }
        return buffer.toString();
    }

    /**
     * 随机字符串
     */
    public static String getNumberUUID(int length) {
        Random random = new Random();
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < length; i++) {
            buffer.append(numbers[random.nextInt(numbers.length)]);
        }
        return buffer.toString();
    }
}
