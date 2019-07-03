package com.ibicn.hr.util;

import com.ibicnCloud.util.FileUtil;
import com.ibicnCloud.util.UtilException;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by zhonghang on 2016/11/1.
 */
public class DownUtil extends Thread {

    private static final Logger logger = Logger.getLogger(DownUtil.class);

    protected static int i = 0;
    protected static ArrayList<String[]> data = new ArrayList<String[]>();

    public void run() {
        while (data.size() > i) {
            String[] patrs = data.get(i)[1].split(",");
            int j = 1;
            for (String path : patrs) {
                try {
                    FileUtil.copyUrlToFile(path, new File("D:\\hetongs\\" + data.get(i)[0] + "_" + data.get(i)[2] + "_" + j + ".jpg"));
                } catch (UtilException e) {
                    logger.error(e);
                }
                j++;
            }
            i++;
        }

    }

    public static void main(String[] args) {
        data = new ERPExcelUtil().readExcelByPath("C:\\Users\\yanghao\\Desktop\\tooduduJ.xls");
        for (int i = 0; i < 10; i++) {
            DownUtil dateUtil = new DownUtil();
            dateUtil.setName("Thread " + i);
            dateUtil.start();
        }
    }
}
