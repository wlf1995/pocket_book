package com.ibicn.hr.config.security.imgcode;

import com.ibicnCloud.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
 * @Description 自定义的登录校验类
 * @Date 14:27 2019/7/19
 **/
public class ImgAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";
    /***
     * @Author 王立方
     * @Description 图形验证码的key
     * @Date 14:36 2019/7/19
     **/
    public static final String SPRING_SECURITY_FORM_IMG_KEY = "imgcode";
    public static final String SPRING_SECURITY_FORM_GUID = "guid";
    private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
    private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;
    private String imgcodeParameter = SPRING_SECURITY_FORM_IMG_KEY;
    private String guidParameter = SPRING_SECURITY_FORM_GUID;
    private boolean postOnly = true;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // ~ Constructors 登录方法默认路径为login,post类型
    // ===================================================================================================
    public ImgAuthenticationFilter() {
        super(new AntPathRequestMatcher("/imglogin", "POST"));
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

        String imgcode = obtainImgCode(request);

        String username = obtainUsername(request);
        String password = obtainPassword(request);
        String guid = obtainGuid(request);
        if (StringUtils.isEmpty(imgcode)) {
            throw new UsernameNotFoundException("验证码不能为空");
        } else { //此处对验证码进行验证
            String code = stringRedisTemplate.opsForValue().get("checkCode" + guid);
            if (StringUtil.isEmpty(code)) {
                throw new UsernameNotFoundException("验证码已失效");
            }
            if (!StringUtil.equals(code, imgcode)) {
                throw new UsernameNotFoundException("验证码错误");
            }
            stringRedisTemplate.delete("checkCode" + guid);
        }
        if (StringUtils.isEmpty(username)) {
            throw new UsernameNotFoundException("帐号不能为空");
        }
        if (StringUtils.isEmpty(password)) {
            throw new UsernameNotFoundException("密码不能为空");
        }

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }
        username = username.trim();

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                username, password);
        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * Enables subclasses to override the composition of the password, such as by
     * including additional values and a separator.
     * <p>
     * This might be used for example if a postcode/zipcode was required in addition to
     * the password. A delimiter such as a pipe (|) should be used to separate the
     * password and extended value(s). The <code>AuthenticationDao</code> will need to
     * generate the expected password in a corresponding manner.
     * </p>
     *
     * @param request so that request attributes can be retrieved
     * @return the password that will be presented in the <code>Authentication</code>
     * request token to the <code>AuthenticationManager</code>
     */
    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(passwordParameter);
    }

    protected String obtainGuid(HttpServletRequest request) {
        return request.getParameter(guidParameter);
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
                              UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }


    protected String obtainImgCode(HttpServletRequest request) {
        return request.getParameter(imgcodeParameter);
    }

    /**
     * Sets the parameter name which will be used to obtain the username from the login
     * request.
     *
     * @param usernameParameter the parameter name. Defaults to "username".
     */
    public void setUsernameParameter(String usernameParameter) {
        Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
        this.usernameParameter = usernameParameter;
    }

    /**
     * Sets the parameter name which will be used to obtain the password from the login
     * request..
     *
     * @param passwordParameter the parameter name. Defaults to "password".
     */
    public void setPasswordParameter(String passwordParameter) {
        Assert.hasText(passwordParameter, "Password parameter must not be empty or null");
        this.passwordParameter = passwordParameter;
    }

    public void setImgcodeParameter(String imgcodeParameter) {
        Assert.hasText(passwordParameter, "imgcode parameter must not be empty or null");
        this.imgcodeParameter = imgcodeParameter;
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

    public final String getPasswordParameter() {
        return passwordParameter;
    }

    public final String getImgcodeParameter() {
        return imgcodeParameter;
    }
}