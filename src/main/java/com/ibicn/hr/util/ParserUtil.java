package com.ibicn.hr.util;


import java.util.ArrayList;

/**
 * Created by Ryan Shi on 2016/8/2.
 */

//字符串反序列化工具类
public class ParserUtil {

    //获得产品的序列号
    public final static String getProductShortName(String s) {
        ArrayList<Character> product = new ArrayList<Character>();
        String code = "";
        for (char c : s.toCharArray()) {
            product.add(c);
        }
        int str = -1, end = -1, mid = -1;

        //获得带-的产品序列号
        if (product.contains('-')) {
            mid = product.indexOf('-');
            for (int i = mid - 1; i > -1; i--) {
                if (CharUtil.isNotCode(product.get(i))) {
                    str = i + 1;
                    break;
                }
            }
            for (int i = mid + 1; i < product.size(); i++) {
                if (CharUtil.isNotCode(product.get(i))) {
                    end = i;
                    break;
                }
            }
            if (str < 0)
                str = 0;
            if (end < 0)
                end = product.size();
            mid = product.lastIndexOf('-');
            code = s.substring(str, end);
            if (code.length() < 3)
                code = "";
            if (mid > end) {
                str = -1;
                end = -1;
                for (int i = mid - 1; i > -1; i--) {
                    if (CharUtil.isNotCode(product.get(i))) {
                        str = i + 1;
                        break;
                    }
                }
                for (int i = mid + 1; i < product.size(); i++) {
                    if (CharUtil.isNotCode(product.get(i))) {
                        end = i;
                        break;
                    }
                }
                if (str < 0)
                    str = 0;
                if (end < 0)
                    end = product.size();
                if (!code.equals(s.substring(str, end)))
                    if (code.length() < 1)
                        code += s.substring(str, end);
                    else
                        code += "(" + s.substring(str, end) + ")";
            }
            if (code.length() > 2)
                return code.trim();
            code = "";
        }

        //获得纯英文和数字的序列号
        for (int i = 0; i < product.size(); i++) {
            if (str < 0) {
                if (CharUtil.isAlphabet(product.get(i)) || CharUtil.isNumber(product.get(i))) {
                    str = i;
                }
            }
            if (product.get(i) == 'L' && i > 0 && CharUtil.isNumber(product.get(i - 1))) {
                str = -1;
            }
            if (product.get(i) == 'G' && product.get(i - 1) == 'K' && i > 1 && CharUtil.isNumber(product.get(i - 2))) {
                str = -1;
            }
            if (product.get(i) == 'g' && product.get(i - 1) == 'k' && i > 1 && CharUtil.isNumber(product.get(i - 2))) {
                str = -1;
            }
            if (str > -1) {
                if (CharUtil.isNotCode(product.get(i)) || i == product.size() - 1) {
                    if (i == product.size() - 1)
                        end = i + 1;
                    else
                        end = i;
                    if (end - str > 2) {
                        if (code.length() > 0)
                            code += "(" + s.substring(str, end) + ")";
                        else
                            code = s.substring(str, end);
                    }
                    str = -1;
                }
            }
        }
        return code.trim();
    }
}
