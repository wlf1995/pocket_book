package com.ibicn.hr.config;

import com.ibicn.hr.shiro.filter.KickoutSessionControlFilter;
import com.ibicn.hr.shiro.filter.LoginFilter;
import com.ibicn.hr.shiro.token.SampleRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/11.
 */
@Configuration
public class ShiroConfig {

    @Value("${spring.redis.host}")
    private String jedisHost;

    @Value("${spring.redis.port}")
    private Integer jedisPort;

    @Value("${spring.redis.password}")
    private String jedisPassword;

    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //配置拦截器
        LinkedHashMap<String, Filter> filtersMap = new LinkedHashMap<String, Filter>();
        filtersMap.put("kickout", getKickoutSessionControlFilter());
        filtersMap.put("login", getLoginFilter());
        //filtersMap.put("authc", new FormAuthenticationFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);
        //配置过滤器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //注意过滤器配置顺序 不能颠倒
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了，登出后跳转配置的loginUrl
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/getEnum", "anon");
        filterChainDefinitionMap.put("/error_500", "anon");
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        filterChainDefinitionMap.put("/views/**", "anon");
        filterChainDefinitionMap.put("/layuiadmin/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/config/loginWx", "anon");
        filterChainDefinitionMap.put("/common/**", "anon");
        filterChainDefinitionMap.put("/user/loginOK", "anon");
        filterChainDefinitionMap.put("/user/loginweixinOK", "anon");
        filterChainDefinitionMap.put("/webView/user/login.html", "anon");
        filterChainDefinitionMap.put("/weixinlogin/index", "anon");
        filterChainDefinitionMap.put("/scheduledTasks/**", "anon");

        //审批路径
        filterChainDefinitionMap.put("/webView/ledger/payApply/payApply_shenpi.html", "anon");
        //发货审批
        filterChainDefinitionMap.put("/webView/wdoodoo/wdoodoo_ledeg_mobelfahuoShenhe.html", "anon");
        //台帐付款核验
        filterChainDefinitionMap.put("/webView/wdoodoo/wdoodoo_ledeg_mobelpayShenhe.html", "anon");
        //指导价审核
        filterChainDefinitionMap.put("/webView/wdoodoo/wdoodoo_ledeg_mobelShenhe.html", "anon");
        //样品配送审核
        filterChainDefinitionMap.put("/webView/wdoodoo/wdoodoo_sampledelivery_shenhesave.html", "anon");

        //根据用户编号进行登录
        filterChainDefinitionMap.put("/user/bianhaoBYloginOk", "anon");

        //获得验证码路径不拦截
        filterChainDefinitionMap.put("/user/getImage", "anon");
        filterChainDefinitionMap.put("/user/getWeixinLogin", "anon");
        filterChainDefinitionMap.put("/user/loginout", "anon");

        //erp获取供应商调整金额
        filterChainDefinitionMap.put("/purplan/getchayi", "anon");
        filterChainDefinitionMap.put("/**", "login");

        //配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
        shiroFilterFactoryBean.setLoginUrl("/webView/user/login.html");
        // 登录成功后要跳转的链接
        // shiroFilterFactoryBean.setSuccessUrl("/index");
        //未授权界面
        // shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SimpleCookie rememberMeCookie() {
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        cookie.setHttpOnly(true);
        //记住我有效期长达30天
        cookie.setMaxAge(2592000);
        return cookie;
    }

    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        rememberMeManager.setCookie(rememberMeCookie());
        rememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
        return rememberMeManager;
    }

    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     * ）
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(0);//散列的次数，比如散列两次，相当于 md5(md5(""));
        return hashedCredentialsMatcher;
    }


    /**
     * 自定义Shiro 的AuthorizingRealm类实现
     *
     * @return
     */
    @Bean
    public SampleRealm myShiroRealm() {
        SampleRealm myShiroRealm = new SampleRealm();
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }

    /**
     * 自定义Shiro 的AccessControlFilter类实现
     * 不能初始化为spring bean，否则配置的不拦截路径会失效
     *
     * @return
     */
    public KickoutSessionControlFilter getKickoutSessionControlFilter() {
        KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
        //用于根据会话ID，获取会话进行踢出操作的；
        kickoutSessionControlFilter.setSessionManager(getSessionManager());
        //是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户
        kickoutSessionControlFilter.setKickoutAfter(false);
        //同一个用户最大的会话数，默认1；比如2的意思是同一个用户允许最多同时两个人登录
        kickoutSessionControlFilter.setMaxSession(1);
        //被踢出后重定向到的地址；
        kickoutSessionControlFilter.setKickoutUrl("/webView/login.html");
        return kickoutSessionControlFilter;
    }

    /**
     * 自定义Shiro 的AccessControlFilter类实现
     * 不能初始化为spring bean，否则配置的不拦截路径会失效
     *
     * @return
     */
    public LoginFilter getLoginFilter() {
        LoginFilter loginFilter = new LoginFilter();
        return loginFilter;
    }

    /**
     * 安全管理器
     *
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        securityManager.setRememberMeManager(rememberMeManager());
        securityManager.setSessionManager(getSessionManager());
        securityManager.setCacheManager(cacheManager());
        return securityManager;
    }

    /**
     * SessionManager
     *
     * @return
     */
    @Bean
    public SessionManager getSessionManager() {
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setSessionDAO(redisSessionDAO());
        defaultWebSessionManager.setGlobalSessionTimeout(1800000);
        defaultWebSessionManager.setCacheManager(cacheManager());
        defaultWebSessionManager.setSessionIdCookie(new SimpleCookie("SHAREJSESSIONIDS"));
        return defaultWebSessionManager;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public RedisManager redisManager() {
        RedisManager manager = new RedisManager();
        manager.setHost(jedisHost);
        manager.setPort(jedisPort);
        //这里是用户session的时长 跟上面的setGlobalSessionTimeout 应该保持一直（上面是1个小时 下面是秒做单位的 我们设置成3600）
        manager.setExpire(60 * 60);
        manager.setPassword(jedisPassword);
        return manager;
    }

    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO sessionDAO = new RedisSessionDAO();
        sessionDAO.setKeyPrefix("ledger_");
        sessionDAO.setRedisManager(redisManager());
        return sessionDAO;
    }

    @Bean(name = "myCacheManager")
    public RedisCacheManager cacheManager() {
        RedisCacheManager manager = new RedisCacheManager();
        manager.setRedisManager(redisManager());
        return manager;
    }
}