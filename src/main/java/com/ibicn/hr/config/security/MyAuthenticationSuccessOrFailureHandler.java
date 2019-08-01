package com.ibicn.hr.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibicn.hr.dao.sys.SystemUserDao;
import com.ibicn.hr.entity.sys.SystemUser;
import com.ibicn.hr.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @param
 * @Author 王立方
 * @Description 自定义的登录结果返回, 可以添加判断, ajax等返回json, 否则转向至页面
 * @Date 9:12 2019/7/22
 * @return
 **/
@Slf4j
@Component
public class MyAuthenticationSuccessOrFailureHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    SystemUserDao userDao;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("登录成功");
        if (isAjax(request)) {
            response.setContentType("application/json;charset=UTF-8");
            HashMap<String, Object> userMap = getUserMap(authentication);
            response.getWriter().write(objectMapper.writeValueAsString(Result.ok(userMap)));
        } else {
            response.sendRedirect("/index");
        }

    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("登陆失败");
        if (isAjax(request)) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(Result.failure(exception.getMessage())));
        } else {
            response.sendRedirect("/login");
        }
    }

    /**
     * @param request
     * @return boolean
     * @Author 王立方
     * @Description 判断是否是ajax
     * @Date 15:33 2019/8/1
     **/
    private boolean isAjax(HttpServletRequest request) {
        if ("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {
            return true;
        }
        return false;
    }

    private HashMap<String, Object> getUserMap(Authentication authentication) {
        SystemUser byUserName = userDao.findByUserName(authentication.getName());
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", byUserName.getId());
        map.put("realName", byUserName.getRealName());
        map.put("userName", byUserName.getUserName());
        map.put("userBianhao", byUserName.getUserBianhao());
        map.put("avatar", byUserName.getAvatar());
        return map;
    }
}