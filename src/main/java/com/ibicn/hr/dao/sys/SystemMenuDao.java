package com.ibicn.hr.dao.sys;

import com.ibicn.hr.entity.sys.SystemMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemMenuDao extends JpaRepository<SystemMenu, Integer>, JpaSpecificationExecutor<SystemMenu> {
    /**
     * 根据用户查询菜单树
     */
    @Query(value = "select distinct t from SystemMenu t join t.roles r join r.users user where user.id= ?1 and t.type=0 and t.parentMenu.id is null order by t.sort,t.id")
    List<SystemMenu> getMenuByUser(Integer id);
     /**
     *获取用户所有角色
     */
    @Query(value = "SELECT DISTINCT (r.id) FROM systemrole r, user_role ur WHERE ur.roleid = r.id AND ur.userid = ?1",nativeQuery = true )
    List<String> getRoleIdByUser(Integer id);
    /**
     *获取用户所有角色
     */
    @Query(value = "SELECT DISTINCT (p.path) FROM systemmenu p, role_menu rp, user_role ur WHERE p.id = rp.menuid AND rp.roleid = ur.roleid AND ur.userid = ?1 AND p.type = ?2  group by p.path",nativeQuery = true )
    List<String> getMenyPathIdByUser(Integer id,Integer type);
}
