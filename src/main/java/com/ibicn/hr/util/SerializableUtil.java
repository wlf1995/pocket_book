package com.ibicn.hr.util;


import org.apache.log4j.Logger;

import java.io.*;

/**
 * Created by zhonghang on 2017/1/11.
 * 序列化工具类
 */
public class SerializableUtil {

    private static final Logger logger = Logger.getLogger(SerializableUtil.class);

    public static byte[] serializable(Object o) {
        ObjectOutputStream out = null;
        ByteArrayOutputStream in = null;

        try {
            in = new ByteArrayOutputStream();
            out = new ObjectOutputStream(in);

            out.writeObject(o);
            byte[] bytes = in.toByteArray();
            return bytes;
        } catch (IOException e) {
            logger.error(e);
        }
        return null;
    }

    public static Object unSerializable(byte[] bytes) {
        ByteArrayInputStream in = null;

        in = new ByteArrayInputStream(bytes);
        try {
            ObjectInputStream o = new ObjectInputStream(in);
            return o.readObject();
        } catch (IOException e) {
            logger.error(e);
        } catch (ClassNotFoundException e) {
            logger.error(e);
        }

        return null;
    }

}
