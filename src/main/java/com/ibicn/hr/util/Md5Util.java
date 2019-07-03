package com.ibicn.hr.util;


import com.ibicnCloud.util.StringUtil;

import java.security.MessageDigest;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


public class Md5Util {


    public static Map<String,Object> sign(Map<String,Object>paras,String app_key){
        paras=filter(paras);
        //时间戳
        Long timeStamp=System.currentTimeMillis()/1000;
        paras.put("timeStamp",timeStamp);
        paras=sort(paras);
        String str=linkStr(paras);
        String sign=md5(str+app_key);
        paras.put("sign",sign);
        return  paras;
    }

    private static String signToUrl(String host,Map<String,Object> paras,String app_key){
        //如果host最后有? 就去掉这个?
        if("?".equals(host.substring(host.length()-1))){
            host=host.substring(0,host.length()-1);
        } paras = sign(paras,app_key);
        return host+"?"+linkStr(paras);
    }
    /**
     * 对数据进行校验
     * @param paras
     * @param app_key
     * @return
     */
    public static boolean verify(Map<String,Object> paras,String app_key){
        String sign= (String) paras.get("sign");
        String timeStamp = paras.get("timeStamp")+"";
        if(StringUtil.isBlank(timeStamp)){
            return false;
        }
        long ts=0;
        long currrey=0;
        try {
            ts = Long.parseLong(timeStamp)+ 300;
            currrey = System.currentTimeMillis()/1000;
        }catch (Exception e){
            return false;
        }
        if(currrey > ts){
            return false;
        }
        Map<String,Object> data=filter(paras);
        data=sort(data);
        String mysign=md5(linkStr(data)+app_key);
        return mysign.equals(sign);
    }

    /**
     * 生成签名
     * @param paras
     * @param app_key
     * @return
     */
    public static String createdSign(Map<String,Object> paras,String app_key){
        Map<String,Object> data=filter(paras);
        data=sort(data);
        String mysign=md5(linkStr(data)+app_key);
        return mysign;
    }

    /**
     * 对数据进行md5加密处理
     * @param url
     * @return
     */
    public static String md5(String url){
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(url.getBytes("utf-8"));
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0){
                    i += 256;
                }
                if (i < 16){
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (Exception e) {
        }
        return result;
    }
    /**
     * 过滤掉sign和空的数据
     * @param paras
     * @return
     */
    private static Map<String,Object> filter(Map<String,Object>paras){
        Iterator<Map.Entry<String,Object>> it=paras.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String, Object> entry = it.next();
            String key=entry.getKey();
            String object=entry.getValue()+"";
            if("sign".equals(key)||object == null || StringUtil.isBlank(object.toString()) || object.toString().length()==0){
                it.remove();
            }
        }
        return paras;
    }

    /**
     * 对数组按照key进行排序
     * @param paras
     * @return
     */
    private static Map<String,Object> sort(Map<String,Object> paras){
        if(paras==null ||paras.isEmpty()){
            return  null;
        }
        Map<String,Object> res=new TreeMap<String, Object>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        res.putAll(paras);
        return  res;
    }

    /**
     * 将map转换为key=val&的形式
     * 如 name=w&age=123
     * @param paras
     * @return
     */
    private static String linkStr(Map<String,Object> paras){
        String link="";
        Iterator<Map.Entry<String,Object>> it=paras.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String, Object> entry = it.next();
            String key=entry.getKey();
            Object object=entry.getValue();
            link+=key+"="+object+"&";
        }
        return  link.substring(0,link.length()-1);
    }
}
