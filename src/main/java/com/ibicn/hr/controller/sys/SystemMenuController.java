package com.ibicn.hr.controller.sys;

import com.ibicn.hr.entity.sys.systemMenu;
import com.ibicn.hr.controller.base.BaseController;
import com.ibicn.hr.util.BaseModel;
import com.ibicn.hr.util.PageResult;
import com.ibicn.hr.util.Result;
import com.ibicnCloud.util.StringUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.List;

@RestController
@RequestMapping("/systemmenu")
public class SystemMenuController extends BaseController {

    @RequestMapping("list")
    public Result list(systemMenu data, BaseModel baseModel) {
        PageResult page = systemMenuServiceI.list(data, baseModel.setOrder("asc").setSort("id,sort"));
        List<systemMenu> content = page.getContent();
        List<Map> list=new ArrayList<>();
        for(systemMenu menu:content){
            list.add(getByMap(menu));
        }
        return Result.ok(PageResult.getPageResult(page,list));
    }

    @RequestMapping("get")
    public Result get(systemMenu data) {
        systemMenu menu = systemMenuServiceI.getById(data.getId());
        return Result.ok(getByMap(menu));
    }

    @RequestMapping("saveOK")
    public Result saveOK(systemMenu data) {
        systemMenu menu = new systemMenu();
        if (data.getParent_id() != null && data.getParent_id().getId() > 0) {
            Integer id = data.getParent_id().getId();
            systemMenu parent = systemMenuServiceI.getById(id);
            if (menu.isIfSetParent(parent)) {
                menu.setParent_id(parent);
            } else {
                return Result.failure("不能设置为上级节点,会造成循环!");
            }
        }
        Result check = check(data);
        if (!check.getCode().equals(Result.StatusCode.SUCCESS_CODE)) {
            return check;
        }
        menu.setPath(data.getPath());
        menu.setMenuName(data.getMenuName());
        menu.setSort(data.getSort());
        menu.setType(data.getType());
        menu.setCreatedTime(new Date());
        systemMenuServiceI.save(menu);
        return Result.ok();
    }

    @RequestMapping("updateOK")
    public Result updateOK(systemMenu data) {
        systemMenu menu = systemMenuServiceI.getById(data.getId());
        if (menu == null) {
            return Result.failure("未获取到菜单");
        }
        if (data.getParent_id() != null && data.getParent_id().getId() > 0) {
            systemMenu parent = systemMenuServiceI.getById(data.getParent_id().getId());
            if (menu.isIfSetParent(parent)) {
                menu.setParent_id(parent);
            } else {
                return Result.failure("不能设置为上级节点,会造成循环!");
            }
        } else {
            menu.setParent_id(null);
        }
        Result check = check(data);
        if (!check.getCode().equals(Result.StatusCode.SUCCESS_CODE)) {
            return check;
        }
        menu.setMenuName(data.getMenuName());
        menu.setSort(data.getSort());
        menu.setType(data.getType());
        menu.setPath(data.getPath());
        systemMenuServiceI.update(menu);
        return Result.ok();
    }

    @RequestMapping("getParent")
    public Result getParentMenu(systemMenu data) {
        List<systemMenu> menuOrNotInMenu = systemMenuServiceI.getMenuOrNotInMenu(data.getMenuName(), data.getId());

        List<Map> list=new ArrayList<>();
        for(systemMenu menu:menuOrNotInMenu){
            list.add(getByMap(menu));
        }
        return Result.ok(list);
    }


    private Result check(systemMenu data) {
        if (StringUtil.isBlank(data.getPath())) {
            return Result.failure("路径不能为空");
        }
        if (StringUtil.isBlank(data.getMenuName())) {
            return Result.failure("名称不能为空");
        }
        if (data.getSort()==null) {
            return Result.failure("顺序不能为空");
        }
        if (StringUtil.isBlank(data.getTypeIndex())) {
            return Result.failure("类型不能为空");
        }
        return Result.ok();
    }

    private Map getByMap(systemMenu menu){
        HashMap<String,Object> map=new HashMap<>();
        map.put("id",menu.getId());
        map.put("name",menu.getMenuName());
        map.put("path",menu.getPath());
        map.put("sort",menu.getSort());
        map.put("type",menu.getType());
        map.put("createdTime",menu.getCreatedTime());
        if (menu.getParent_id()!=null){
            systemMenu menu1=new systemMenu();
            menu1.setId(menu.getParent_id().getId());
            menu1.setMenuName(menu.getParent_id().getMenuName());
            map.put("parentMenu",menu1);
        }
        return map;
    }
}
