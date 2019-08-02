package com.ibicn.hr.service.sys;

import com.ibicn.hr.ENUM.EnumMenuType;
import com.ibicn.hr.entity.sys.systemMenu;
import com.ibicn.hr.entity.sys.systemUser;
import com.ibicn.hr.service.base.BaseServiceI;
import com.ibicn.hr.util.BaseModel;
import com.ibicn.hr.util.PageResult;

import java.util.List;

public interface SystemMenuServiceI extends BaseServiceI<systemMenu> {
    /**
     * @Author 王立方
     * @Description 分页查询
     * @Date 14:15 2019/7/2
     * @param data, baseModel
     * @return org.springframework.data.domain.Page
     **/
    PageResult list(systemMenu data, BaseModel baseModel);
    /**
     * 根据名称搜索菜单,如果传递一个菜单ID则不获取该菜单ID其他的
     * @param name
     * @param id
     * @return
     */
    List<systemMenu> getMenuOrNotInMenu(String name, int id);

    /**
     * 获取全部菜单
     * @return
     */
    List<systemMenu> getAllMenu(EnumMenuType type);

    /**
     * 根据角色ID获取菜单
     * @param ids
     * @return
     */
    List<systemMenu> getMenyByRoleIds(String ids);


    /**
     * 获取父节点为空的父级菜单
     * @return
     */
    List<systemMenu> getPanentMenu(EnumMenuType type);

    /**
     * 根据用户获取菜单树
     */
    List<systemMenu> getMenuByUser(systemUser user);

    /**
     * 获取用户所有角色
     */
    List<String> getRoleIdByUser(systemUser user);

    /**
     * 获取用户所有菜单路径
     */
    List<String> getMenyPathIdByUser(systemUser user, EnumMenuType type);

    systemMenu getById(Integer id);

}
