package com.ibicn.hr.util;

import java.math.BigDecimal;

/**
 * Created by glgf122 on 2017/7/28.
 */
public class ErpBigDecimalUtil {
    /**
     * 加
     */
    public static BigDecimal addDecimal(BigDecimal a, BigDecimal b, int mumFractionDigits) {
        BigDecimal result = a.add(b);
        return formatDecimal(result, mumFractionDigits);
    }

    /**
     * 减
     */
    public static BigDecimal subDecimal(BigDecimal a, BigDecimal b, int mumFractionDigits) {
        BigDecimal result = a.subtract(b);
        return formatDecimal(result, mumFractionDigits);
    }

    /**
     * 乘
     */
    public static BigDecimal multDecimal(BigDecimal a, BigDecimal b, int mumFractionDigits) {
        BigDecimal result = a.multiply(b);
        return formatDecimal(result, mumFractionDigits);
    }

    /**
     * 除
     */
    public static BigDecimal diviDecimal(BigDecimal a, BigDecimal b, int mumFractionDigits) {
        BigDecimal result = a.divide(b, mumFractionDigits, BigDecimal.ROUND_HALF_UP);
        return result;
    }

    /**
     * 比较大小
     */
    public static int compareDecimal(BigDecimal a, BigDecimal b, int mumFractionDigits) {
        if (formatDecimal(a, mumFractionDigits).compareTo(formatDecimal(b, mumFractionDigits)) < 0) {
            return -1;
        } else if (formatDecimal(a, mumFractionDigits).compareTo(formatDecimal(b, mumFractionDigits)) == 0) {
            return 0;
        } else if (formatDecimal(a, mumFractionDigits).compareTo(formatDecimal(b, mumFractionDigits)) > 0) {
            return 1;
        } else {
            return -1;
        }
    }


    /**
     * 传递数值，进行保留小数操作
     */
    public static BigDecimal formatDecimal(BigDecimal bigDecimal, int mumFractionDigits) {
        //通过除法来保留两位小数
        bigDecimal = bigDecimal.divide(new BigDecimal("1"), mumFractionDigits, BigDecimal.ROUND_HALF_UP);
        return bigDecimal;
    }
}
