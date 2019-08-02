package com.ibicn.hr.dao.sys;

import com.ibicn.hr.dao.base.BaseDaoI;
import com.ibicn.hr.entity.sys.SystemMenu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemMenuDao extends BaseDaoI<SystemMenu> {
    /**
     * 根据用户查询菜单树
     */
    @Query(value = "select distinct t from SystemMenu t join t.roles r join r.users user where user.id= ?1 and t.type=0 and t.parent_id.id is null order by t.sort,t.id")
    List<SystemMenu> getMenuByUser(Integer id);

    /**
     * 获取用户所有角色
     */
    @Query(value = "SELECT DISTINCT (r.id) FROM systemrole r, user_role ur WHERE ur.roleid = r.id AND ur.userid = ?1", nativeQuery = true)
    List<String> getRoleIdByUser(Integer id);

    /**
     * 获取用户所有角色
     */
    @Query(value = "SELECT DISTINCT (p.path) \n" +
            "FROM systemmenu p, systemRoleMenu rp, systemUserRole ur \n" +
            "WHERE \n" +
            "p.id = rp.systemMenu_id \n" +
            "AND rp.systemRole_id = ur.systemRole_id \n" +
            "AND ur.systemUser_id = ?1 \n" +
            "AND p.type = ?2  \n" +
            "group by p.path", nativeQuery = true)
    List<String> getMenyPathIdByUser(Integer id, Integer type);
}
