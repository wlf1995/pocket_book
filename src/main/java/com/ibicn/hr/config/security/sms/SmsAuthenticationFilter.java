package com.ibicn.hr.config.security.sms;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author 王立方
 * @Description 自定义的登录校验类 手机短信登录
 * @Date 14:27 2019/7/19
 **/
public class SmsAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    //手机号
    public static final String SPRING_SECURITY_FORM_PHONENUM_KEY = "phoneNum";
    /***
     * @Author 王立方
     * @Description 短信验证码的key
     * @Date 14:36 2019/7/19
     **/
    public static final String SPRING_SECURITY_FORM_SMS_KEY = "smscode";

    private String usernameParameter = SPRING_SECURITY_FORM_PHONENUM_KEY;
    private String smscodeParameter = SPRING_SECURITY_FORM_SMS_KEY;
    private boolean postOnly = true;

    // ~ Constructors 登录方法默认路径为login,post类型
    // ===================================================================================================
    public SmsAuthenticationFilter() {
        super(new AntPathRequestMatcher("/smsLoginOk", "POST"));
    }
// ~ Methods
    // ========================================================================================================

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String smscode = obtainImgCode(request);

        String username = obtainUsername(request);

        if (StringUtils.isEmpty(username)) {
            throw new UsernameNotFoundException("帐号不能为空");
        }
        if (StringUtils.isEmpty(smscode)) {
            throw new UsernameNotFoundException("短信验证码不能为空");
        }else {
            //校验短信验证码
            if(!smscode.equals("12345678")){
                throw new UsernameNotFoundException("短信验证码错误");
            }
        }

        if (username == null) {
            username = "";
        }

        if (smscode == null) {
            smscode = "";
        }
        username = username.trim();

        SmsAuthenticationToken authRequest = new SmsAuthenticationToken(
                username, smscode);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }


    /**
     * Enables subclasses to override the composition of the username, such as by
     * including additional values and a separator.
     *
     * @param request so that request attributes can be retrieved
     * @return the username that will be presented in the <code>Authentication</code>
     * request token to the <code>AuthenticationManager</code>
     */
    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter(usernameParameter);
    }


    /**
     * Provided so that subclasses may configure what is put into the authentication
     * request's details property.
     *
     * @param request     that an authentication request is being created for
     * @param authRequest the authentication request object that should have its details
     *                    set
     */
    protected void setDetails(HttpServletRequest request,
                              SmsAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }


    protected String obtainImgCode(HttpServletRequest request) {
        return request.getParameter(smscodeParameter);
    }

    /**
     * Sets the parameter name which will be used to obtain the username from the login
     * request.
     *
     * @param usernameParameter the parameter name. Defaults to "username".
     */
    public void setUsernameParameter(String usernameParameter) {
        Assert.hasText(usernameParameter, "用户名不能为空");
        this.usernameParameter = usernameParameter;
    }

    public void setImgcodeParameter(String smscodeParameter) {
        Assert.hasText(smscodeParameter, "短信验证码不能为空");
        this.smscodeParameter = smscodeParameter;
    }

    /**
     * Defines whether only HTTP POST requests will be allowed by this filter. If set to
     * true, and an authentication request is received which is not a POST request, an
     * exception will be raised immediately and authentication will not be attempted. The
     * <tt>unsuccessfulAuthentication()</tt> method will be called as if handling a failed
     * authentication.
     * <p>
     * Defaults to <tt>true</tt> but may be overridden by subclasses.
     */
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getUsernameParameter() {
        return usernameParameter;
    }


    public final String getImgcodeParameter() {
        return smscodeParameter;
    }
}