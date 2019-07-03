package com.ibicn.hr.shiro.filter;

import com.ibicn.hr.bean.sys.SystemUser;
import com.ibicnCloud.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class FramePermissionsAuthorizationFilter extends PermissionsAuthorizationFilter {
	private Logger logger = LoggerFactory.getLogger(FramePermissionsAuthorizationFilter.class);

	@Override
	public boolean isAccessAllowed(ServletRequest request,
                                   ServletResponse response, Object mappedValue) throws IOException {
		Subject user = SecurityUtils.getSubject();
		SystemUser shiroSystemUser = (SystemUser) user.getPrincipal();
		HttpServletRequest req = (HttpServletRequest) request;
		Subject subject = getSubject(request, response);
		String uri = req.getRequestURI();
		String requestURL = req.getRequestURL().toString();
		String contextPath = req.getContextPath();

		int i = uri.indexOf(contextPath);
		if (i > -1) {
			uri = uri.substring(i + contextPath.length());
		}
		if (StringUtil.isBlank(uri)) {
			uri = "/";
		}
		boolean permitted = false;
		if ("/".equals(uri)) {
			permitted = true;
		} else {
			permitted = subject.isPermitted(uri);
		}
		String isqx = "否";
		if (permitted) {
			isqx = "是";
		}


		logger.info("有人访问了" + uri);
		return permitted;

	}
}
