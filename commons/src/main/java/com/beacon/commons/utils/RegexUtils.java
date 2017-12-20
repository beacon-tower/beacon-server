package com.beacon.commons.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <h2>正则表达式工具类，提供一些常用的正则表达式</h2>
 * 正则工具
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/9/19
 */
public class RegexUtils {

    /**
     * 匹配全网IP的正则表达式
     */
    private static final String IP_REGEX = "^((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))$";

    /**
     * 手机号码:
     * 13[0-9], 14[5,7], 15[0, 1, 2, 3, 5, 6, 7, 8, 9], 17[0, 1, 6, 7, 8], 18[0-9]
     * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188,198
     * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186,166
     * 电信号段: 133,149,153,170,173,177,180,181,189,199
     */
    private static final String MOBILE_NUMBER_REGEX = "^1(3[0-9]|4[57]|5[0-35-9]|6[6]|7[0135678]|8[0-9]|9[89])\\d{8}$";

    /**
     * 匹配邮箱的正则表达式
     * <br>"www."可省略不写
     */
    private static final String EMAIL_REGEX = "^(www\\.)?\\w+@\\w+(\\.\\w+)+$";

    /**
     * 匹配汉子的正则表达式，个数限制为一个或多个
     */
    private static final String CHINESE_REGEX = "^[\u4e00-\u9f5a]+$";

    /**
     * 匹配正整数的正则表达式，个数限制为一个或多个(包含0)
     */
    private static final String POSITIVE_INTEGER_REGEX = "^\\d+$";

    /**
     * 电话号码的正则表达式
     */
    private static final String TEL_PHONE_REGEX = "^(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)$";

    /**
     * 匹配身份证号的正则表达式
     */
    private static final String ID_CARD = "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";

    /**
     * 匹配邮编的正则表达式
     */
    private static final String ZIP_CODE = "^\\d{6}$";

    /**
     * 匹配URL的正则表达式
     */
    private static final String URL = "^(([hH][tT]{2}[pP][sS]?)|([fF][tT][pP]))\\:\\/\\/[wW]{3}\\.[\\w-]+\\.\\w{2,4}(\\/.*)?$";

    /**
     * 匹配给定的字符串是否是一个邮箱账号，"www."可省略不写
     *
     * @param text 给定的字符串
     * @return true：是
     */
    public static boolean isEmail(String text) {
        return text.matches(EMAIL_REGEX);
    }

    /**
     * 匹配给定的字符串是否是一个手机号码，支持130——139、150——153、155——159、180、183、185、186、188、189号段
     *
     * @param text 给定的字符串
     * @return true：是
     */
    public static boolean isMobileNumber(String text) {
        return text.matches(MOBILE_NUMBER_REGEX);
    }

    /**
     * 匹配给定的字符串是否是一个电话号码
     *
     * @param text 给定的字符串
     * @return true：是
     */
    public static boolean isTelPhone(String text) {
        return text.matches(TEL_PHONE_REGEX);
    }

    /**
     * 匹配给定的字符串是否是一个全网IP
     *
     * @param text 给定的字符串
     * @return true：是
     */
    public static boolean isIp(String text) {
        return text.matches(IP_REGEX);
    }

    /**
     * 匹配给定的字符串是否全部由汉子组成
     *
     * @param text 给定的字符串
     * @return true：是
     */
    public static boolean isChinese(String text) {
        return text.matches(CHINESE_REGEX);
    }

    /**
     * 验证给定的字符串是否全部由正整数组成
     *
     * @param text 给定的字符串
     * @return true：是
     */
    public static boolean isPositiveInteger(String text) {
        return text.matches(POSITIVE_INTEGER_REGEX);
    }

    /**
     * 验证给定的字符串是否是身份证号
     * <br>
     * <br>身份证15位编码规则：dddddd yymmdd xx p
     * <br>dddddd：6位地区编码
     * <br>yymmdd：出生年(两位年)月日，如：910215
     * <br>xx：顺序编码，系统产生，无法确定
     * <br>p：性别，奇数为男，偶数为女
     * <br>
     * <br>
     * <br>身份证18位编码规则：dddddd yyyymmdd xxx y
     * <br>dddddd：6位地区编码
     * <br>yyyymmdd：出生年(四位年)月日，如：19910215
     * <br>xxx：顺序编码，系统产生，无法确定，奇数为男，偶数为女
     * <br>y：校验码，该位数值可通过前17位计算获得
     * <br>前17位号码加权因子为 Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ]
     * <br>验证位 Y = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ]
     * <br>如果验证码恰好是10，为了保证身份证是十八位，那么第十八位将用X来代替 校验位计算公式：Y_P = mod( ∑(Ai×Wi),11 )
     * <br>i为身份证号码1...17 位; Y_P为校验码Y所在校验码数组位置
     *
     * @param text 文本
     * @return boolean
     */
    public static boolean isIdCard(String text) {
        return IDNumberUtils.isValid(text);
    }

    /**
     * 验证给定的字符串是否是邮编
     *
     * @param text 文本
     * @return boolean
     */
    public static boolean isZipCode(String text) {
        return text.matches(ZIP_CODE);
    }

    /**
     * 验证给定的字符串是否是URL，仅支持http、https、ftp
     *
     * @param text url字符串
     * @return boolean
     */
    public static boolean isURL(String text) {
        return text.matches(URL);
    }

    /**
     * 验证给定的字符串是否是数字
     *
     * @param text url字符串
     * @return boolean
     */
    public static boolean isNumber(String text) {
        return text.matches(POSITIVE_INTEGER_REGEX);
    }

    /**
     * 只能输入字母和汉字 这个返回的是过滤之后的字符串
     *
     * @param text 文字
     * @return String
     */
    public static String checkInputPro(String text) {
        try {
            String regEx = "[^a-zA-Z\u4E00-\u9FA5]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(text);
            return m.replaceAll("").trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
