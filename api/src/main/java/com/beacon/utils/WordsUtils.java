package com.beacon.utils;

/**
 * 文字工具
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/22
 */
public class WordsUtils {

    private static final String CN_REGEX = "[^(\\u4e00-\\u9fa5，。《》？；’‘：“”【】、）（……￥！·)]";
    private static final String NON_CN_REGEX = "[^(a-zA-Z0-9`\\-=\']";

    public static int getWordsNum(String content) {
        //中文单词
        String cnWords = content.replaceAll(CN_REGEX, "");
        int cnWordsCount = cnWords.length();
        //非中文单词
        String nonCnWords = content.replaceAll(NON_CN_REGEX, " ");
        int nonCnWordsCount = 0;
        String[] ss = nonCnWords.split(" ");
        for (String s : ss) {
            if (s.trim().length() != 0) {
                nonCnWordsCount++;
            }
        }
        return cnWordsCount + nonCnWordsCount;
    }

    public static void main(String[] args) {
        String content = "我的小狗，叫旺财！  喜欢吃肉。sss . sssseeeeee";
        System.out.println(WordsUtils.getWordsNum(content));
    }

}
