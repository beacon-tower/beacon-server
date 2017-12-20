package com.beacon.commons.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期操作帮助类
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/5/26
 */
public class DateUtils {

    /**
     * 根据指定日期格式转换为字符串
     *
     * @param date    待转换日期对象Date
     * @param pattern 日期格式，SimpleDateFormat能够识别的格式
     * @return 成功返回Date，失败返回null
     */
    public static String toString(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    public static String toString(Calendar calendar, String pattern) {
        return toString(calendar.getTime(), pattern);
    }

    /**
     * 对指定的日期添加或减去指定的时间
     * 例如：减去5天
     * <code>add(Calendar.DAY_OF_MONTH, -5)</code>.
     *
     * @param date   指定的日期
     * @param field  指定的时间域，月、周、天等
     * @param amount 指定的时间间隔
     * @return 返回处理后的日期
     */
    public static Date add(Date date, int field, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, amount);
        return calendar.getTime();
    }

    /**
     * 根据指定的日期，计算对应的自然开始时间
     *
     * @param date  指定的日期
     * @param field 指定的时间域，仅支持日、周、月、年
     * @return 成功返回日期，失败返回null
     * @throws IllegalArgumentException
     */
    public static Date getBeginDate(Date date, int field) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        switch (field) {
            // 日，找凌晨
            case Calendar.DAY_OF_YEAR: {
            }
            case Calendar.DAY_OF_MONTH: {
                break;
            }

            // 周，找周一
            case Calendar.DAY_OF_WEEK: {
                int day = calendar.get(Calendar.DAY_OF_WEEK);
                if (day == 1) {
                    calendar.add(Calendar.WEEK_OF_MONTH, -1);
                }
                calendar.set(Calendar.DAY_OF_WEEK, 2);
                break;
            }

            // 月，找月初
            case Calendar.MONTH: {
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                break;
            }

            // 年，找年初
            case Calendar.YEAR: {
                calendar.set(Calendar.MONTH, 0);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                break;
            }

            default:
                throw new IllegalArgumentException();
        }

        return calendar.getTime();
    }

    /**
     * 根据指定的日期，计算对应的自然结束时间
     *
     * @param date  指定的日期
     * @param field 指定的时间域，仅支持日、周、月、年
     * @return 成功返回日期，失败返回null
     * @throws IllegalArgumentException
     */
    public static Date getEndDate(Date date, int field) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        switch (field) {
            // 日，找半夜
            case Calendar.DAY_OF_YEAR: {
            }
            case Calendar.DAY_OF_MONTH: {
                break;
            }

            // 周，找周日
            case Calendar.DAY_OF_WEEK: {
                int day = calendar.get(Calendar.DAY_OF_WEEK);
                if (day != 1) {
                    calendar.add(Calendar.WEEK_OF_MONTH, 1);
                    calendar.set(Calendar.DAY_OF_WEEK, 1);
                }
                break;
            }

            // 月，找月末
            case Calendar.MONTH: {
                // 先找下月1号，然后减一天
                calendar.add(Calendar.MONTH, 1);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                break;
            }

            // 年，找年末
            case Calendar.YEAR: {
                calendar.set(Calendar.MONTH, 11);
                return getEndDate(calendar.getTime(), Calendar.MONTH);
            }

            default:
                throw new IllegalArgumentException();
        }

        return calendar.getTime();
    }

    /**
     * 将指定时间范围的日期全部统计出来
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 指定时间范围的日期
     */
    public static List<Date> getDateList(Date beginDate, Date endDate) {
        Calendar beginCal = Calendar.getInstance();
        beginCal.setTime(beginDate);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);

        List<Date> dateList = new ArrayList<>();
        while (beginCal.before(endCal)) {
            dateList.add(beginCal.getTime());
            beginCal.add(Calendar.DAY_OF_YEAR, 1);
        }
        return dateList;
    }
}
