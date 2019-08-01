package com.ibicn.hr.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibicn.hr.config.security.imgcode.ImgAuthenticationFilter;
import com.ibicn.hr.config.security.imgcode.ImgAuthenticationProvider;
import com.ibicn.hr.config.security.sms.SmsAuthenticationFilter;
import com.ibicn.hr.config.security.sms.SmsAuthenticationProvider;
import com.ibicn.hr.config.security.wx.WXAuthenticationFilter;
import com.ibicn.hr.config.security.wx.WXAuthenticationProvider;
import com.ibicn.hr.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义认证
 * Created by wangt on 2018/7/29.
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private ObjectMapper objectMapper;
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private MyAuthenticationSuccessOrFailureHandler authenticationSuccessHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(imgAuthenticationProvider())
                .authenticationProvider(smsAuthenticationProvider())
                .authenticationProvider(wxAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.addFilterAfter(imgAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(smsAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(wxAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        //设置允许同源的iframe嵌套
        http.headers().frameOptions().sameOrigin();
        http.authorizeRequests()
                // 如果有允许匿名的url，填在下面
                .antMatchers("/sendSmsCode", "/getImage").permitAll()
                .anyRequest().authenticated()
                // 设置登陆页
                .and().formLogin().loginPage("/login")
                // 设置登陆成功页
                .defaultSuccessUrl("/index").permitAll()
                //设置退出路径
                .and().logout().logoutUrl("/logout").logoutSuccessHandler(new LogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                log.info(authentication.getName()+"退出登录");
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(objectMapper.writeValueAsString(Result.ok()));
            }
        }).permitAll()
                // 关闭CSRF跨域
                .and().csrf().disable();
    }


    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/common/**", "/css/**", "/fonts/**", "/img/**", "/js/**", "/layuiadmin/**", "/webView/user/login.html");
    }


    @Bean
    public ImgAuthenticationProvider imgAuthenticationProvider() {
        ImgAuthenticationProvider imgAuthenticationProvider = new ImgAuthenticationProvider();
        imgAuthenticationProvider.setUserDetailsService(userDetailsService);
        return imgAuthenticationProvider;
    }

    @Bean
    public SmsAuthenticationProvider smsAuthenticationProvider() {
        SmsAuthenticationProvider smsAuthenticationProvider = new SmsAuthenticationProvider();
        smsAuthenticationProvider.setUserDetailsService(userDetailsService);
        return smsAuthenticationProvider;
    }

    @Bean
    public WXAuthenticationProvider wxAuthenticationProvider() {
        WXAuthenticationProvider wxAuthenticationProvider = new WXAuthenticationProvider();
        wxAuthenticationProvider.setUserDetailsService(userDetailsService);
        return wxAuthenticationProvider;
    }

    @Bean
    public ImgAuthenticationFilter imgAuthenticationFilter() {
        ImgAuthenticationFilter imgAuthenticationFilter = new ImgAuthenticationFilter();
        imgAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        imgAuthenticationFilter.setAuthenticationFailureHandler(authenticationSuccessHandler);
        try {
            imgAuthenticationFilter.setAuthenticationManager(super.authenticationManager());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imgAuthenticationFilter;
    }

    @Bean
    public SmsAuthenticationFilter smsAuthenticationFilter() {
        SmsAuthenticationFilter smsAuthenticationFilter = new SmsAuthenticationFilter();
        smsAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        smsAuthenticationFilter.setAuthenticationFailureHandler(authenticationSuccessHandler);
        try {
            smsAuthenticationFilter.setAuthenticationManager(super.authenticationManager());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return smsAuthenticationFilter;
    }

    @Bean
    public WXAuthenticationFilter wxAuthenticationFilter() {
        WXAuthenticationFilter wxAuthenticationFilter = new WXAuthenticationFilter();
        wxAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        wxAuthenticationFilter.setAuthenticationFailureHandler(authenticationSuccessHandler);
        try {
            wxAuthenticationFilter.setAuthenticationManager(super.authenticationManager());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wxAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}