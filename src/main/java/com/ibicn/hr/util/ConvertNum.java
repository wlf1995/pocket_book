package com.ibicn.hr.util;

public class ConvertNum {
    /**
     * 还有一种方法可以在转换的过程中不考虑连续0的情况，然后对最终的结果进行一次遍历合并连续的零
     */
    protected final static String[] ChineseNum = new String[]{"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};

    public static String NumToChinese(double num) {
        if (num > 99999999999999.99 || num < -99999999999999.99)
            throw new IllegalArgumentException("参数值超出允许范围 (-99999999999999.99 ～ 99999999999999.99)！");
        boolean negative = false;// 正负标号
        if (num < 0) {
            negative = true;
            num = num * (-1);
        }
        long temp = Math.round(num * 100);
        int numFen = (int) (temp % 10);// 分
        temp = temp / 10;
        int numJiao = (int) (temp % 10);// 角
        temp = temp / 10;
        // 此时temp只包含整数部分
        int[] parts = new int[20];// 将金额整数部分分为在0-9999之间数的各个部分
        int numParts = 0;// 记录把原来金额整数部分分割为几个部分
        for (int i = 0; ; i++) {
            if (temp == 0)
                break;
            int part = (int) (temp % 10000);
            parts[i] = part;
            temp = temp / 10000;
            numParts++;
        }
        boolean beforeWanIsZero = true;// 标志位，记录万的下一级是否为0
        String chineseStr = "";
        for (int i = 0; i < numParts; i++) {
            String partChinese = partConvert(parts[i]);
            if (i % 2 == 0) {
                if ("".equals(partChinese))
                    beforeWanIsZero = true;
                else
                    beforeWanIsZero = false;
            }
            if (i != 0) {
                if (i % 2 == 0)// 亿的部分
                    chineseStr = "亿" + chineseStr;
                else {
                    if ("".equals(partChinese) && !beforeWanIsZero)// 如果“万”对应的 part 为 0，而“万”下面一级不为 0，则不加“万”，而加“零”
                        chineseStr = "零" + chineseStr;
                    else {
                        if (parts[i - 1] < 1000 && parts[i - 1] > 0)// 如果万的部分不为0，而万前面的部分小于1000大于0，则万后面应该跟零
                            chineseStr = "零" + chineseStr;
                        chineseStr = "万" + chineseStr;
                    }
                }
            }
            chineseStr = partChinese + chineseStr;
        }
        if ("".equals(chineseStr))// 整数部分为0，则表示为零元
            chineseStr = ChineseNum[0];
        else if (negative)// 整数部分部位0，但是为负数
            chineseStr = "负" + chineseStr;
        chineseStr = chineseStr + "元";
        if (numFen == 0 && numJiao == 0) {
            chineseStr = chineseStr + "整";
        } else if (numFen == 0) {// 0分
            chineseStr = chineseStr + ChineseNum[numJiao] + "角";
        } else {
            if (numJiao == 0)
                chineseStr = chineseStr + "零" + ChineseNum[numFen] + "分";
            else
                chineseStr = chineseStr + ChineseNum[numJiao] + "角" + ChineseNum[numFen] + "分";
        }
        return chineseStr;
    }

    // 转换拆分后的每个部分，0-9999之间
    public static String partConvert(int partNum) {
        if (partNum < 0 || partNum > 10000) {
            throw new IllegalArgumentException("参数必须是大于等于0或小于10000的整数");
        }
        String[] units = new String[]{"", "拾", "佰", "仟"};
        int temp = partNum;
        String partResult = new Integer(partNum).toString();
        int partResultLength = partResult.length();
        boolean lastIsZero = true;// 记录上一位是否为0
        String chineseStr = "";
        for (int i = 0; i < partResultLength; i++) {
            if (temp == 0)// 高位无数字
                break;
            int digit = temp % 10;
            if (digit == 0) {
                if (!lastIsZero)// 如果前一个数字不是0则在当前汉字串前加零
                    chineseStr = "零" + chineseStr;
                lastIsZero = true;
            } else {
                chineseStr = ChineseNum[digit] + units[i] + chineseStr;
                lastIsZero = false;
            }
            temp = temp / 10;
        }
        return chineseStr;
    }


    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00小时00分00秒";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + "分" + unitFormat(second) + "秒";
            } else {
                hour = minute / 60;
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + "小时" + unitFormat(minute) + "分" + unitFormat(second) + "秒";
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

}