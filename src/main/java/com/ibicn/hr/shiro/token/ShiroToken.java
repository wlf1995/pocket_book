package com.ibicn.hr.shiro.token;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Created by 陈书山 on 2016/11/29.
 */
public class ShiroToken extends UsernamePasswordToken  implements java.io.Serializable{
	
	private static final long serialVersionUID = -6451794657814516274L;

	public ShiroToken(String username, String pswd) {
		super(username,pswd);
		this.pswd = pswd ;
	}
	public ShiroToken(String username, String pswd, String loginType, String userBianhao) {
		super(username, pswd);
		this.pswd = pswd;
		this.userBianhao = userBianhao;
		this.loginType = loginType;
	}

	private String userBianhao ;

	private String loginType = "0";// 0为用户密码登录，1为微信登录
	
	/** 登录密码[字符串类型] 因为父类是char[] ] **/
	private String pswd ;

	public String getPswd() {
		return pswd;
	}


	public void setPswd(String pswd) {
		this.pswd = pswd;
	}


	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getUserBianhao() {
		return userBianhao;
	}

	public void setUserBianhao(String userBianhao) {
		this.userBianhao = userBianhao;
	}
}
