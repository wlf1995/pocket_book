package com.ibicn.hr.util;

import java.text.DecimalFormat;

/**
 * Created by shira on 2016/8/2.
 */

//字符工具类
public class PercentageUtil {

    /**
     * 计算a在b中占多少百分比（结果保留两位小数）
     *
     * @param a
     * @param b
     * @return
     */
    public final static String isAlphabet(double a, double b) {
        String baifenbi = "";// 接受百分比的值
        double fen = a / b;
        DecimalFormat df1 = new DecimalFormat("##.00%");
        baifenbi = df1.format(fen);
        return baifenbi;
    }

}
