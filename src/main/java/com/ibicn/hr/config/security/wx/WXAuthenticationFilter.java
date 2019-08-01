package com.ibicn.hr.config.security.wx;

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
 * @Description 自定义的登录校验类 微信authcode登录
 * @Date 14:27 2019/7/19
 **/
public class WXAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public static final String SPRING_SECURITY_FORM_AUTHCODE_KEY = "authcode";
    private String authcodeParameter = SPRING_SECURITY_FORM_AUTHCODE_KEY;
    private boolean postOnly = true;

    // ~ Constructors 登录方法默认路径为login,post类型
    // ===================================================================================================
    public WXAuthenticationFilter() {
        super(new AntPathRequestMatcher("/wxLoginOk", "POST"));
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

        String authcode = obtainAuthCode(request);

        if (StringUtils.isEmpty(authcode)) {
            throw new UsernameNotFoundException("帐号不能为空");
        }
        if (authcode == null) {
            authcode = "";
        }

        authcode = authcode.trim();

        WXAuthenticationToken authRequest = new WXAuthenticationToken(
                authcode);

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
    protected String obtainAuthcod(HttpServletRequest request) {
        return request.getParameter(authcodeParameter);
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
                              WXAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }


    protected String obtainAuthCode(HttpServletRequest request) {
        return request.getParameter(authcodeParameter);
    }

    /**
     * Sets the parameter name which will be used to obtain the username from the login
     * request.
     *
     * @param authcodeParameter the parameter name. Defaults to "authcode".
     */
    public void setAuthcodeParameter(String authcodeParameter) {
        Assert.hasText(authcodeParameter, "authcode不能为空");
        this.authcodeParameter = authcodeParameter;
    }

    public String getAuthcodeParameter() {
        return authcodeParameter;
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


}