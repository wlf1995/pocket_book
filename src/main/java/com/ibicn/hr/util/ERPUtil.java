package com.ibicn.hr.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhonghang on 2017-09-12.
 */
public final class ERPUtil {
    /**
     * 通过URL获取文件类型
     *
     * @return
     */
    /*private static String getFileType(String url){
        if(url == null || "".equals(url)){

        }
    }*/
    public static String getDateStrByDate(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    public static Date parseDate(String dateStr, String pattern) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.parse(dateStr);
    }

}

