package com.ibicn.hr.shiro.filter;

import com.alibaba.fastjson.JSONObject;
import com.ibicn.hr.ENUM.EnumRequestType;
import com.ibicn.hr.bean.sys.SystemUser;
import com.ibicn.hr.service.sys.SystemUserServiceI;
import com.ibicn.hr.shiro.token.TokenManager;
import com.ibicn.hr.util.CookieUtil;
import com.ibicn.hr.util.Md5Util;
import com.ibicn.hr.util.RequestData;
import com.ibicnCloud.util.DateUtil;
import com.ibicnCloud.util.StringUtil;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 陈书山 on 2016/11/29.
 */
public class LoginFilter extends AccessControlFilter {
    final static Class<LoginFilter> CLASS = LoginFilter.class;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SystemUserServiceI userServiceI;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        //拿到当前的登录人
        SystemUser token = TokenManager.getToken();
        if (null != token || isLoginRequest(request, response)) {// && isEnabled()
            int dayNumber = 0;
            if (token.getUpdatePassWordDay() != null) {
                dayNumber = DateUtil.getDateDiffInDay(token.getUpdatePassWordDay(), new Date());
            }
            return Boolean.TRUE;
        }
        //拿到当前的登录人的cookie，然后进行登陆
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String userName = CookieUtil.getCookieValue(httpServletRequest, "userName");
        if (StringUtil.isNotBlank(userName) || isLoginRequest(request, response)) {
            SystemUser user = new SystemUser();
            user.setUserName(userName);
            user.setPassword("cookieLogin");
            SystemUser entity = TokenManager.loginByWx(user, false);
            return Boolean.TRUE;
        }
        if (ShiroFilterUtils.isAjax(request)) {// ajax请求
            Map<String, String> resultMap = new HashMap<String, String>();
            logger.debug("当前用户没有登录，并且是Ajax请求！");
            resultMap.put("login_status", "300");
            resultMap.put("message", "用户未登陆");//当前用户没有登录！
            ShiroFilterUtils.out(response, resultMap);
        } else {
            WebUtils.issueRedirect(request, response, "/webView/user/login.html");
        }
        return Boolean.FALSE;

    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
            throws Exception {
        return Boolean.FALSE;
    }

    private boolean isTure(String ip, ServletRequest request, ServletResponse response) {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("ipAddress", "123456");
        parameter.put("systemID", "m24GFnnuvF1111");
        parameter.put("timeStamp", System.currentTimeMillis() / 1000);
        parameter = Md5Util.sign(parameter, "BTHHTEgm8HpaLJ4B418zvGe4ymALpxfM");
        JSONObject object = RequestData.getData("http://188.188.3.223:8088/manage/systemSafety/checkIPAddress.mvc", parameter, EnumRequestType.Post);
        System.out.println(object.toJSONString());
        if (object != null && StringUtil.equals(object.getString("status"), "1")) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }


}
