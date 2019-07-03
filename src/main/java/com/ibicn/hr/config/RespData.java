package com.ibicn.hr.config;

import com.alibaba.fastjson.JSONObject;
import com.ibicnCloud.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: Mick
 * @create: 2018-05-03
 */
public class RespData {

    private static final Logger logger = Logger.getLogger(RespData.class);
    /**
     * 将字符串响应回前台
     */
    public static void writeString(String errmsg, Object data) {
        writeString(errmsg, data, 0);
    }

    /**
     * 将字符串响应回前台
     */
    public static void writeString(String errmsg, Object data, int count) {
        try {
            JSONObject json = new JSONObject();
            json.put("code", StringUtil.isNotBlank(errmsg) ? 1 : 0);
            json.put("msg", errmsg == null ? "" : errmsg);
            json.put("data", data == null ? new JSONObject() : data);
            json.put("count", count);

            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();
            HttpServletResponse response = requestAttributes.getResponse();

            response.setContentType("text/html;charset=utf-8");
            String miwen = mobileJiami(json.toJSONString());
            String callback = request.getParameter("callback");
            if (StringUtil.isNotBlank(callback)) {
                miwen = callback + "(" + miwen + ")";
            }
            response.getWriter().write(miwen);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
        }
    }

    /**
     * 往mobile发信时进行加密
     *
     * @param str
     *            明文
     * @return 密文
     */
    public static String mobileJiami(String str) {
        // str = str == null ? "" : str.trim();
        // str = Escape.escape(str);

        // 压缩数据
        // str = GZipUtil.yasuo(str);

        // String key = TranscodeUtil.strToBase64Str(MOBILEMIMA);
        // String miwen = "";
        // try {
        // miwen = SecretUtil.AESEncrypt(str, key, MOBILEMIMA);
        // } catch (Exception e) {
        // return "";
        // }
        // return miwen.replaceAll("\\+", "\\@");
        return str;
    }

}
