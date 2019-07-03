package com.ibicn.hr.shiro.filter;


import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by 陈书山 on 2016/11/29.
 */
public class ShiroFilterUtils {
	private static Logger logger = LoggerFactory.getLogger(ShiroFilterUtils.class);

	//登录页面
	static final String LOGIN_URL = "/webView/user/login.html";
	//踢出登录提示
	final static String KICKED_OUT = "/webView/user/login.html";
	//没有权限提醒
	final static String UNAUTHORIZED = "/403.do";
	/**
	 * 是否是Ajax请求
	 * @param request
	 * @return
	 */
	public static boolean isAjax(ServletRequest request){
		return "XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"));
	}
	
	/**
	 * response 输出JSON
	 * @param resultMap
	 * @throws IOException
	 */
	public static void out(ServletResponse response, Map<String, String> resultMap){
		
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			out.println(JSONObject.toJSONString(resultMap));
		} catch (Exception e) {
			logger.error("输出JSON报错。", e);
		}finally{
			if(null != out){
				out.flush();
				out.close();
			}
		}
	}
}
