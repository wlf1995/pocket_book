package com.ibicn.hr.util;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by yanghao on 2017/4/24.
 */
public class ManagerTomcat {

    private static final Logger logger = Logger.getLogger(ManagerTomcat.class);

    public void close() {
        Process process = null; // 调用外部程序
        try {
            process = Runtime.getRuntime().exec("cmd /c  F:\\apache-tomcat-8.0.32\\bin\\shutdown.bat");

            final InputStream in = process.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder buf = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null)
                buf.append(line);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public void start() {
        Process process = null; // 调用外部程序
        try {
            process = Runtime.getRuntime().exec("cmd /c startup");
//                process.waitFor();
//                new Thread(Thread.currentThread()).start();
            final InputStream in = process.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder buf = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null)
                buf.append(new String(line.getBytes("ISO8859-1"), "UTF-8"));
        } catch (IOException e) {
            logger.error(e);
        }
//            catch (InterruptedException e) {
//                e.printStackTrace();
//            }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        ManagerTomcat con = new ManagerTomcat();
        con.start();
//            con.close();
    }
}
