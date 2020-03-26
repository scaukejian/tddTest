package com.kj.tdd.tddTest.utils;

import org.springframework.cglib.core.Local;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期格式化工具类
 */
public class DateUtil {

    private static ThreadLocal<SimpleDateFormat> simpleDateFormatThreadLocal = new ThreadLocal<>();

    public static SimpleDateFormat getThreadLocalDateFormat(String pattern) {
        SimpleDateFormat simpleDateFormat = simpleDateFormatThreadLocal.get();
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat(pattern);
            simpleDateFormatThreadLocal.set(simpleDateFormat);
        }
        return simpleDateFormat;
    }

    public static String formatDate(Date date, String pattern) {
        return getThreadLocalDateFormat(pattern).format(date);
    }

    public static Date parseDate(String dateStr, String pattern) throws ParseException {
        return getThreadLocalDateFormat(pattern).parse(dateStr);
    }

    public static String formatLocalDate(LocalDate localDate, String pattern) {
        return localDate.format(getDateTimeFormatter(pattern));
    }

    public static LocalDate parseLocalDate(String localDateStr, String pattern) {
        return LocalDate.parse(localDateStr, getDateTimeFormatter(pattern));
    }

    public static String formatLocalDateTime(LocalDateTime localDateTime, String pattern) {
        return localDateTime.format(getDateTimeFormatter(pattern));
    }

    public static LocalDateTime parseLocalDateTime(String localDateTimeStr, String pattern) {
        return LocalDateTime.parse(localDateTimeStr, getDateTimeFormatter(pattern));
    }

    public static DateTimeFormatter getDateTimeFormatter(String pattern) {
        return DateTimeFormatter.ofPattern(pattern);
    }

    public static void main(String[] args) throws Exception{
        String str = "2020-10-19 12:22:23";
        String pattern = "yyyy-MM-dd HH:mm:ss";
        //日期格式化
        System.out.println(formatDate(new Date(), pattern));
        System.out.println(formatLocalDate(LocalDate.now(), pattern.substring(0, 10)));
        System.out.println(formatLocalDateTime(LocalDateTime.now(), pattern));
        //时间字符串转日期
        System.out.println(parseDate(str, pattern));
        System.out.println(parseLocalDate(str.substring(0, 10), pattern.substring(0, 10)));
        System.out.println(parseLocalDateTime(str, pattern));
    }
}
