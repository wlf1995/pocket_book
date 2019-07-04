package com.ibicn.hr.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @param
 * @Author 王立方
 * @Description 根据日期返回格式化后的字符串
 * @Date 20:08 2019/6/21
 * @return
 **/
public class DateUtil {
    public static final String format_DATE = "yyyy-MM-dd";
    public static final String format_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    public static String format(String format, Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String forma1t = simpleDateFormat.format(date);
        return forma1t;
    }
    public static Date parse(String format, String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date forma1t = null;
        try {
            forma1t = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return forma1t;
    }
    public static String getFormatDate(Date date) {
        return format(format_DATE, date);
    }

    public static String getFormatDateTime(Date date) {
        return format(format_DATE_TIME, date);
    }

    public static Date getParseDate(String date) {
        return parse(format_DATE, date);
    }

    public static Date getParseDateTime(String date) {
        return parse(format_DATE_TIME, date);
    }
}
