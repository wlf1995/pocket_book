package com.ibicn.hr.service.sys;

import com.ibicn.hr.ENUM.EnumMenuType;
import com.ibicn.hr.entity.sys.SystemMenu;
import com.ibicn.hr.entity.sys.SystemUser;
import com.ibicn.hr.service.base.BaseServiceI;

import java.util.List;

public interface SystemMenuServiceI extends BaseServiceI<SystemMenu> {
    /**
     * 根据名称搜索菜单,如果传递一个菜单ID则不获取该菜单ID其他的
     *
     * @param name
     * @param id
     * @return
     */
    List<SystemMenu> getMenuOrNotInMenu(String name, int id);

    /**
     * 获取全部菜单
     *
     * @return
     */
    List<SystemMenu> getAllMenu(EnumMenuType type);

    /**
     * 根据角色ID获取菜单
     *
     * @param ids
     * @return
     */
    List<SystemMenu> getMenyByRoleIds(String ids);


    /**
     * 获取父节点为空的父级菜单
     *
     * @return
     */
    List<SystemMenu> getPanentMenu(EnumMenuType type);

    /**
     * 根据用户获取菜单树
     */
    List<SystemMenu> getMenuByUser(SystemUser user);

    /**
     * 获取用户所有角色
     */
    List<String> getRoleIdByUser(SystemUser user);

    /**
     * 获取用户所有菜单路径
     */
    List<String> getMenyPathIdByUser(SystemUser user, EnumMenuType type);

    SystemMenu getById(Integer id);

}
