package com.ibicn.hr.util;

/**
 * Created by shira on 2016/8/2.
 */

//字符工具类
public class CharUtil {

    //判定字符是否为字母
    public final static boolean isAlphabet(char c) {
        if ((c - 'A' < 26 && c - 'A' > -1) || (c - 'a' < 26 && c - 'a' > -1))
            return true;
        return false;
    }

    //判定字符是否为数字
    public final static boolean isNumber(char c) {
        if ((c - '0' < 10 && c - '0' > -1))
            return true;
        return false;
    }

    //判定字符是否为斜杠
    public final static boolean isSlash(char c) {
        if (c - '/' == 0)
            return true;
        return false;
    }

    //判定字符是否为反斜杠
    public final static boolean isBackSlash(char c) {
        if (c - '\\' == 0)
            return true;
        return false;
    }

    //判定
    public final static boolean isDash(char c) {
        if (c - '-' == 0)
            return true;
        return false;
    }

    public final static boolean isNotCode(char c) {
        return (!isNumber(c)) && (!isSlash(c)) && (!isAlphabet(c)) && (!isDash(c)) && (!isBackSlash(c));
    }

}
