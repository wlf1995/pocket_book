package com.ibicn.hr.config.security.sms;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 自定义认证,手机验证码登录
 * Created by wangt on 2018/8/18.
 */
public class SmsAuthenticationProvider implements AuthenticationProvider {
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //用户名登录
        String username = authentication.getName();
        //检查用户名有效性
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        //构建一个认证后的令牌
        SmsAuthenticationToken authenticationResult = new SmsAuthenticationToken(userDetails, authentication.getDetails(), userDetails.getAuthorities());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return SmsAuthenticationToken.class.isAssignableFrom(aClass);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
