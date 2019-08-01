package com.ibicn.hr.config.security.wx;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 自定义认证,手机验证码登录
 * Created by wangt on 2018/8/18.
 */
public class WXAuthenticationProvider implements AuthenticationProvider {
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //用户名登录
        String authcode = authentication.getName();
        //根据authcode 获取到用户登录信息
        String username="test";

        //根据获取到的用户信息,进行登录
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        //构建一个认证后的令牌
        WXAuthenticationToken authenticationResult = new WXAuthenticationToken(userDetails, userDetails.getAuthorities());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return WXAuthenticationToken.class.isAssignableFrom(aClass);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
