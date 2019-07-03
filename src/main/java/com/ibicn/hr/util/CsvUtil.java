package com.ibicn.hr.util;

import com.ibicnCloud.util.CollectionUtil;
import com.ibicnCloud.util.FileUtil;
import com.ibicnCloud.util.StringUtil;
import com.ibicnCloud.util.UtilException;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zmf on 16/12/21.
 */
public class CsvUtil {

    public static int createdCsv(String PATH, List list, String title, String[] rowsName) {
        List values = new LinkedList();
        StringBuffer sb = new StringBuffer();
        for (int j = 0; j < StringUtil.size(rowsName); j++) {
            if (j == 0) {
                sb.append(rowsName[j]);
            } else {
                sb.append("	" + rowsName[j]);
            }
        }
        values.add(sb.toString());//增加标题

        for (int i = 0; i < CollectionUtil.size(list); i++) {
            String[] args = (String[]) list.get(i);
            sb = new StringBuffer();
            for (int j = 0; j < StringUtil.size(args); j++) {
                if (j == 0) {
                    sb.append(args[j]);
                } else {
                    sb.append("	" + args[j]);
                }
            }
            values.add(sb.toString());
        }
        try {
            FileUtil.writeListToFile(PATH, values);
        } catch (UtilException e) {
            return 0;
        }
        return 1;
    }

    public static int createdCsv(String PATH, String content, String title, String[] rowsName) {
        List values = new LinkedList();
        StringBuffer sb = new StringBuffer();
        for (int j = 0; j < StringUtil.size(rowsName); j++) {
            if (j == 0) {
                sb.append(rowsName[j]);
            } else {
                sb.append("	" + rowsName[j]);
            }
        }
        //增加标题
        values.add(sb.toString());

        values.add(content);
        try {
            FileUtil.writeListToFile(PATH, values);
        } catch (UtilException e) {
            return 0;
        }
        return 1;
    }
}
