package com.ibicn.hr.util;

import com.ibicnCloud.util.StringUtil;
import com.ibicnCloud.util.http.HttpManager;
import com.ibicnCloud.util.http.HttpRequest;
import com.ibicnCloud.util.http.HttpResponse;
import org.apache.log4j.Logger;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.*;

/**
 * Created by zmf on 2017/4/19.
 */
public class HttpUtil {

    private static final Logger logger = Logger.getLogger(HttpUtil.class);

    static String BASEURL = "https://api.superid.me/v1";

    static String URL_CREAT_GROUP = BASEURL + "/group/create";
    static String URL_GET_GROUP_INFO = BASEURL + "/group/:id/info";
    static String URL_GET_GROUP_USERS = BASEURL + "/group/:id/users";
    static String URL_GET_GROUP_LIST = BASEURL + "/group/list";
    static String URL_GROUP_INFO = BASEURL + "/group/:id";
    static String URL_GROUP_USER = BASEURL + "/group/:id/users";

    static String SOURCE_APP_ID = "9a82f54464e3e39623cd1809";
    static String APP_ID = "9a82f54464e3e39623cd1809";
    static String SECRET = "3AEotxzWVZGOWYbh0tpI55jKmNcLy43n";


    public static void main(String[] args) {
//        createGroup();
//        addUserToGroup("7c5a942e59d2d21b488839bd0f1c5458","28639c7270b6f531264ab0155d1e6f11","918f559b0575fd06c62f500b2d63f1ad");
//        getGroupInfo("7c5a942e59d2d21b488839bd0f1c5458");
        getGroupUsers("7c5a942e59d2d21b488839bd0f1c5458");
    }

    public static void createGroup() {
        HttpRequest request = new HttpRequest(URL_CREAT_GROUP, "2");

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("app_id", APP_ID);
        params.put("timestamp", System.currentTimeMillis() + "");
        params.put("noncestr", StringUtil.getUUID());
        params.put("name", "ceshizu");
        params.put("source_app_id", SOURCE_APP_ID);

        try {
            String signature = getSignature(params, SECRET);
            params.put("signature", signature);

            Iterator iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry) iterator.next();
                request.addParameter(entry.getKey(), entry.getValue());
            }

            HttpResponse response = null;
            String cookieStr = "";

            response = HttpManager.send(request);
            if (response.getStatusCode() == 200) {
                cookieStr = StringUtil.format(response.getBody());
            }
        } catch (Exception e) {
            logger.error(e);
        }
//        7c5a942e59d2d21b488839bd0f1c5458
    }

    //添加用户到指定集合 Done
    public static void addUserToGroup(String groupId, String openId, String groupUid) {
        String url = URL_GROUP_USER;
        url = url.replace(":id", groupId);
        HttpRequest request = new HttpRequest(url, "2");
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("app_id", APP_ID);
        params.put("timestamp", System.currentTimeMillis() + "");
        params.put("noncestr", StringUtil.getUUID());
        if (openId != null) {
            params.put("open_id", openId);
        }
        if (groupUid != null) {
            params.put("group_uid", groupUid);
        }
        try {
            String signature = getSignature(params, SECRET);
            params.put("signature", signature);


            Iterator iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry) iterator.next();
                request.addParameter(entry.getKey(), entry.getValue());
            }

            HttpResponse response = null;
            String cookieStr = "";

            response = HttpManager.send(request);
            if (response.getStatusCode() == 200) {
                cookieStr = StringUtil.format(response.getBody());
            }
        } catch (Exception ex) {

        }
    }

    /*
     * 签名生成算法
     * @param HashMap<String,String> params 请求参数集，所有参数必须已转换为字符串类型，值已进行encode
     * @param String secret 后端应用签名密钥 secret
     * @return 签名
     * @throws IOException
     */
    public static String getSignature(HashMap<String, String> params, String secret) throws Exception {
        // 先将参数以其参数名的字典序升序进行排序
        Map<String, String> sortedParams = new TreeMap<String, String>(params);
        Set<Map.Entry<String, String>> entrys = sortedParams.entrySet();

        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder basestring = new StringBuilder();
        for (Map.Entry<String, String> param : entrys) {
            basestring.append(param.getKey()).append("=").append(param.getValue()).append("&");
        }
        basestring.delete(basestring.length() - 1, basestring.length()).append(":").append(secret);
        // 使用MD5对待签名串求签
        byte[] bytes;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            bytes = md5.digest(basestring.toString().getBytes("UTF-8"));
        } catch (GeneralSecurityException ex) {
            throw new Exception(ex);
        }

        // 将MD5输出的二进制结果转换为小写的十六进制
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex);
        }
        return sign.toString().toUpperCase();
    }


    //获取指定集合信息 Done
    public static void getGroupInfo(String groupId) {
        String url = URL_GET_GROUP_INFO;
        url = url.replace(":id", groupId);

        HttpRequest request = new HttpRequest(url, "1");
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("app_id", APP_ID);
        params.put("timestamp", System.currentTimeMillis() + "");
        params.put("noncestr", StringUtil.getUUID());
        try {
            String signature = getSignature(params, SECRET);
            params.put("signature", signature);


            Iterator iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry) iterator.next();
                request.addParameter(entry.getKey(), entry.getValue());
            }

            HttpResponse response = null;
            String cookieStr = "";

            response = HttpManager.send(request);
            if (response.getStatusCode() == 200) {
                cookieStr = StringUtil.format(response.getBody());
            }
        } catch (Exception ex) {
        }
    }


    //获取指定集合用户列表 Done
    public static void getGroupUsers(String groupId) {
        String url = URL_GROUP_USER;
        url = url.replace(":id", groupId);

        HttpRequest request = new HttpRequest(url, "1");
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("app_id", APP_ID);
        params.put("timestamp", System.currentTimeMillis() + "");
        params.put("noncestr", StringUtil.getUUID());
        try {
            String signature = getSignature(params, SECRET);
            params.put("signature", signature);


            Iterator iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry) iterator.next();
                request.addParameter(entry.getKey(), entry.getValue());
            }

            HttpResponse response = null;
            String cookieStr = "";

            response = HttpManager.send(request);
            if (response.getStatusCode() == 200) {
                cookieStr = StringUtil.format(response.getBody());
            }
        } catch (Exception ex) {
        }
    }
}
