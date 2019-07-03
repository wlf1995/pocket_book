package com.ibicn.hr.util;

import java.nio.CharBuffer;
import java.util.HashMap;

/**
 * Created by zhonghang on 2017/5/18.
 */
public class MessageUtil {
    public static HashMap<String, String> getMessage(CharBuffer msg) {
        HashMap<String, String> map = new HashMap<String, String>();
        String msgString = msg.toString();
        String m[] = msgString.split(",");
        map.put("fromName", m[0]);
        map.put("toName", m[1]);
        map.put("content", m[2]);
        return map;
    }
}
