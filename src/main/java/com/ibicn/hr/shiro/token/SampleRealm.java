package com.ibicn.hr.shiro.token;


import com.ibicn.hr.bean.sys.SystemUser;
import com.ibicn.hr.service.sys.SystemUserServiceI;
import com.ibicn.hr.util.Md5Util;
import com.ibicnCloud.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created by 陈书山 on 2016/11/29.
 */
public class SampleRealm extends AuthorizingRealm {

    @Autowired
    SystemUserServiceI userService;


    public SampleRealm() {
        super();
    }

    /**
     * 认证信息，主要针对用户登录，
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        ShiroToken token = (ShiroToken) authcToken;
        SystemUser systemUser = new SystemUser();
        systemUser.setUserName(token.getUsername());
        systemUser = userService.findByUserName(systemUser.getUserName());
        if (null == systemUser) {
            throw new AccountException("帐号或密码不正确！");
        }
        if(StringUtil.isNotBlank(token.getPswd())){
            //用户编号登录，不校验密码
            if(token.getLoginType().equals("1")){

            }else
            //微信登陆用户loginType==1，不校验密码
            if (!systemUser.getPassword().equals(Md5Util.md5(token.getPswd()))&& token.getLoginType().equals("0")) {
                String msg = "Submitted credentials for token [" + token + "] did not match the expected credentials.";
                throw new IncorrectCredentialsException(msg);
            }
        } else{
            throw new AuthenticationException("A CredentialsMatcher must be configured in order to verify credentials during authentication.  If you do not wish for credentials to be examined, you can configure an " + AllowAllCredentialsMatcher.class.getName() + " instance.");
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(systemUser, Md5Util.md5(token.getPswd()), getName());
        return simpleAuthenticationInfo;
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //用户主键
        int userId = TokenManager.getUserId();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(null);
        info.setStringPermissions(null);
        return info;
    }

    /**
     * 清空当前用户权限信息
     */
    public void clearCachedAuthorizationInfo() {
        PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principalCollection, getName());
        super.clearCachedAuthorizationInfo(principals);
    }

    /**
     * 指定principalCollection 清除
     */
    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(
                principalCollection, getName());
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    protected void assertCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) throws AuthenticationException {
        ShiroToken shiroToken = (ShiroToken) token;
        if(StringUtil.isNotBlank(shiroToken.getPswd())){
            if (!info.getCredentials().equals(Md5Util.md5(shiroToken.getPswd()))) {
                String msg = "Submitted credentials for token [" + token + "] did not match the expected credentials.";
                throw new IncorrectCredentialsException(msg);
            }
        } else {
            throw new AuthenticationException("A CredentialsMatcher must be configured in order to verify credentials during authentication.  If you do not wish for credentials to be examined, you can configure an " + AllowAllCredentialsMatcher.class.getName() + " instance.");
        }
    }
}
