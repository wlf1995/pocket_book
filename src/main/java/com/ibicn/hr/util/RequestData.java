package com.ibicn.hr.util;

import com.alibaba.fastjson.JSONObject;
import com.ibicn.hr.ENUM.EnumRequestType;
import com.ibicnCloud.util.StringUtil;
import com.ibicnCloud.util.http.HttpManager;
import com.ibicnCloud.util.http.HttpRequest;
import com.ibicnCloud.util.http.HttpResponse;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * 发送请求基类
 * Created by zhonghang on 2016/7/22.
 */
public class RequestData {

    private static Logger logger = Logger.getLogger(RequestData.class);


    public static JSONObject getDataBySign(String url, Map<String ,Object> parameter, String token){
        parameter=Md5Util.sign(parameter,StringUtil.format(token));
        return getData(url , parameter , EnumRequestType.Post , "UTF-8");
    }

    public static JSONObject getData(String url, Map<String ,Object> parameter , EnumRequestType requestType){
        return getData(url , parameter , requestType , "UTF-8");
    }

    public static JSONObject getData(String url, Map<String ,Object> parameter , EnumRequestType requestType , String charset ){
        HttpRequest request = new HttpRequest(url , requestType.getIndex() + "", charset);
        request.setTimeout(1000);
        if(parameter != null) {
            for (String o : parameter.keySet()) {
                request.addParameter(o, parameter.get(o) == null ? "" : parameter.get(o).toString());
            }
        }
        HttpResponse response = null;
        String cookieStr ="";
        try {
            response = HttpManager.send(request);
            if (response.getStatusCode() == 200) {
                cookieStr = StringUtil.format(response.getBody(charset));
                if (!StringUtil.isEmpty(cookieStr)) {
                    JSONObject object = JSONObject.parseObject(cookieStr);
                    return object;
                }
            }
        }catch (Exception e) {
            logger.error("请求失败："+url+",方法："+parameter.get("c")+",返回内容："+cookieStr);
        }
        return null;

    }

}
