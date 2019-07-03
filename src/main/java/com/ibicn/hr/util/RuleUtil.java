package com.ibicn.hr.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhonghang on 2016/8/12.
 */
public class RuleUtil {
    public List<BigDecimal[]> getJieTiByString(String content) {
        //验证阶段返利  1-99:150;200-199:250;200-299:350;300-*:600;
        if (content.indexOf("*") == -1) {
            return null;
        }
        String[] duanwei = content.replace("：", ":").replace("；", ";").replace(" ", "").split(";");
        List<BigDecimal[]> result = new ArrayList<>();
        for (int i = 0; i < duanwei.length; i++) {
            String zhi = duanwei[i];
            boolean flage = false;
            if (zhi.contains("-") && zhi.contains(":") && zhi.indexOf(":") != zhi.length() && zhi.indexOf("-") != 0 && zhi.indexOf("-") < zhi.indexOf(":")) {
                String start = zhi.substring(0, zhi.indexOf("-"));
                String end = zhi.substring(zhi.indexOf("-") + 1, zhi.indexOf(":"));
                String value = zhi.substring(zhi.indexOf(":") + 1, zhi.length());
                //是否为最后一个段位
                if (end.equals("*") && i == duanwei.length - 1) {
                    end = start;
                } else if (end.equals("*") && i != duanwei.length - 1) {
                    return null;
                }
                //开始吨数              结束吨数                价位
                result.add(new BigDecimal[]{new BigDecimal(start), new BigDecimal(end), new BigDecimal(value)});
                //开始吨数要小于结束吨数，并且价格要大于0
                if (result.get(result.size() - 1)[0].compareTo(result.get(result.size() - 1)[1]) > 0 || result.get(result.size() - 1)[2].compareTo(BigDecimal.ZERO) < 0) {
                    flage = true;
                }
                if (result.size() == 1) {
                    continue;
                } else {
                    //每个阶段价位是否连续
                    if (result.get(result.size() - 2)[1].add(BigDecimal.ONE).compareTo(result.get(result.size() - 1)[0]) != 0 && result.get(result.size() - 1)[2].compareTo(BigDecimal.ZERO) < 0) {
                        flage = true;
                    }
                }
            } else {
                flage = true;
            }
            if (flage) {
                return null;
            }
        }
        return result;
    }
}
