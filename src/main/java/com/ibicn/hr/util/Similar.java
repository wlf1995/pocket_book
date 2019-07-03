package com.ibicn.hr.util;

import com.ibicnCloud.util.StringUtil;

import java.math.BigDecimal;

/**
 * Created by zhonghang on 2017/5/8.
 */
public class Similar {
    /**
     * 得到相似度，大于80的
     *
     * @return
     */
    public static boolean getLikePe(String keyword_new, String keyword_old) {
        //相似度  = 有效字段长度  / 总字段长度
        String[] keyword_news = StringUtil.format(keyword_new).split("");
        String[] keyword_olds = StringUtil.format(keyword_old).split("");
        if (keyword_news.length <= 1 || keyword_olds.length <= 1) {
            return false;
        } else {
            int num = 0;
            for (int i = 0; i < keyword_news.length; i++) {
                if (keyword_old.indexOf(keyword_news[i]) >= 0) {
                    num++;
                }
            }
            for (int i = 0; i < keyword_olds.length; i++) {
                if (keyword_new.indexOf(keyword_olds[i]) >= 0) {
                    num++;
                }
            }
            BigDecimal bili = new BigDecimal(num).divide(new BigDecimal(keyword_news.length + keyword_olds.length), 2, BigDecimal.ROUND_HALF_UP);
            if (bili.compareTo(new BigDecimal("0.8")) >= 0) {
                return true;
            }
        }
        return false;
    }
}
