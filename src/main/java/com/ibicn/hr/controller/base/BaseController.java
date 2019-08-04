package com.ibicn.hr.controller.base;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibicn.hr.entity.sys.SystemMenu;
import com.ibicn.hr.entity.sys.SystemUser;
import com.ibicn.hr.service.sys.*;
import com.ibicnCloud.util.CollectionUtil;
import com.ibicnCloud.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

/**
 * @author wzg
 * @ClassName BaseController
 * @date 2018年5月4日
 */
public class BaseController {
    @Autowired
    protected SystemUserServiceI userService;

    @Autowired
    protected SystemConfigServiceI systemConfigService;

    @Autowired
    protected SystemMenuServiceI systemMenuServiceI;

    @Autowired
    protected SystemRoleServiceI systemRoleServiceI;

    protected HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    protected HttpServletResponse getResponse() {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.setCharacterEncoding("UTF-8");
        return response;
    }


    /**
     * 得到当前登录用户
     */
    public SystemUser getUser() {
        Principal principal = getRequest().getUserPrincipal();
        String userName = principal.getName();
        SystemUser curUser = null;
        if (StringUtil.isNotBlank(userName)) {
            curUser = userService.findByUserName(userName);
        } else {
            return null;
        }
        return curUser;
    }

    /**
     * 判断是否是移动端
     *
     * @param request
     * @return
     */
    public boolean isMobileClient(HttpServletRequest request) {
        String agent = request.getHeader("User-Agent").toLowerCase();
        if (agent.indexOf("ipad") > -1 || agent.indexOf("android") > -1 || agent.indexOf("phone") > -1
                || agent.indexOf("tablet") > -1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 递归查找子菜单
     *
     * @param id       当前菜单id
     * @param rootMenu 要查找的列表
     * @return
     */
    protected JSONArray getChild(Integer id, List<SystemMenu> rootMenu) {
        // 子菜单
        JSONArray array = new JSONArray();
        for (SystemMenu menu : rootMenu) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (menu.getParent_id() != null) {
                if (menu.getParent_id().getId().equals(id)) {
                    JSONObject object = new JSONObject();
                    object.put("id", menu.getId());
                    object.put("name", menu.getMenuName());
                    array.add(object);
                }
            }
        }
        // 递归退出条件
        if (CollectionUtil.size(array) == 0) {
            return null;
        }
        // 把子菜单的子菜单再循环一遍
        for (int i = 0; i < array.size(); i++) {// 没有url子菜单还有子菜单
            JSONObject object = array.getJSONObject(i);
            object.put("childs", getChild(object.getInteger("id"), rootMenu));
        }
        return array;
    }
}
