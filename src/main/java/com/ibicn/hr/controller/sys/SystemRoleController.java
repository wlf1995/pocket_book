package com.ibicn.hr.controller.sys;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibicn.hr.controller.base.BaseController;
import com.ibicn.hr.entity.sys.systemMenu;
import com.ibicn.hr.entity.sys.systemRole;
import com.ibicn.hr.util.BaseModel;
import com.ibicn.hr.util.PageResult;
import com.ibicn.hr.util.Result;
import com.ibicnCloud.util.CollectionUtil;
import com.ibicnCloud.util.StringUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/systemrole")
public class SystemRoleController extends BaseController {

    @RequestMapping("list")
    public Result list(systemRole data, BaseModel baseModel) {
        PageResult pr = systemRoleServiceI.list(data, baseModel.setOrder("asc"));
        List<systemRole> content = pr.getContent();
        List<Map> list = new ArrayList<>();
        for (systemRole role : content) {
            list.add(getByMap(role));
        }
        return Result.ok(PageResult.getPageResult(pr, list));
    }

    @RequestMapping("get")
    public Result get(systemRole data) {
        systemRole role = systemRoleServiceI.getById(data.getId());
        return Result.ok(getByMap(role));
    }

    @RequestMapping("saveOK")
    public Result saveOK(systemRole data) {
        systemRole role = new systemRole();
        Result check = check(data);
        if (!check.getCode().equals(Result.StatusCode.SUCCESS_CODE)) {
            return check;
        }
        role.setRoleName(data.getRoleName());
        role.setCreatedTime(new Date());
        systemRoleServiceI.save(role);
        return Result.ok();
    }

    @RequestMapping("updateOK")
    public Result updateOK(systemRole data) {
        systemRole role = systemRoleServiceI.getById(data.getId());
        if (role == null) {
            return Result.failure("未获取到角色");
        }
        Result check = check(data);
        if (!check.getCode().equals(Result.StatusCode.SUCCESS_CODE)) {
            return check;
        }
        role.setRoleName(data.getRoleName());
        systemRoleServiceI.update(role);
        return Result.ok();
    }

    /**
     * 授权页面数据展示
     *
     * @param data
     */
    @RequestMapping("getAuthoData")
    public Result getAllMenu(systemRole data) {
        systemRole role = systemRoleServiceI.getById(data.getId());
        if (role == null) {
            return Result.failure("未获取到授权角色");
        }
        Integer[] checks = new Integer[CollectionUtil.size(role.getMenus())];
        if (CollectionUtil.size(role.getMenus()) > 0) {
            int i = 0;
            for (systemMenu item : role.getMenus()) {
                if (CollectionUtil.size(item.getChilds()) > 0 && item.getParent_id() == null) {
                    continue;
                }
                checks[i++] = item.getId();
            }

        }
        if (CollectionUtil.size(role.getMenus()) > 0) {
            String ids = "";
            for (systemMenu menu : role.getMenus()) {
                if (StringUtil.isBlank(ids)) {
                    ids = menu.getId() + "";
                } else {
                    ids += "," + menu.getId();
                }
            }
        }
        List<systemMenu> AllMenu = systemMenuServiceI.getAllMenu(null);
        List<systemMenu> parentMenu = systemMenuServiceI.getPanentMenu(null);
        JSONArray menus = new JSONArray();
        for (systemMenu menu : parentMenu) {
            JSONObject object = new JSONObject();
            object.put("id", menu.getId());
            object.put("name", menu.getMenuName());
            object.put("childs", super.getChild(menu.getId(), AllMenu));
            menus.add(object);
        }

        JSONObject result = new JSONObject();
        result.put("menus", menus);
        result.put("checks", checks);
        return Result.ok(result);
    }

    @RequestMapping("saveAutho")
    public Result saveAutho(Integer roleid, String ids) {
        systemRole role = systemRoleServiceI.getById(roleid);
        if (role == null) {
            return Result.failure("未获取到授权角色");
        }
        if (StringUtil.isBlank(ids)) {
            return Result.failure("菜单为空");
        }
        String[] id = ids.split(",");
        Set<systemMenu> menus = new HashSet<>();
        for (int i = 0; i < CollectionUtil.size(id); i++) {
            systemMenu menu = systemMenuServiceI.getById(StringUtil.parseInt(id[i]));
            if (menu == null) {
                return Result.failure("未获取到授权菜单");
            }
            menus.add(menu);
        }
        role.setMenus(menus);
        systemRoleServiceI.update(role);
        return Result.ok();
    }


    private Result check(systemRole data) {
        if (StringUtil.isBlank(data.getRoleName())) {
            return Result.failure("名称不能为空");
        }
        return Result.ok();
    }

    private Map getByMap(systemRole role) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", role.getId());
        map.put("name", role.getRoleName());
        map.put("createdTime", role.getCreatedTime());
        return map;
    }
}
