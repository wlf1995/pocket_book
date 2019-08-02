package com.ibicn.hr.entity.sys;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="SystemRole")
public class SystemRole implements Serializable {
    /**
     * 角色的名称
     */
    @Column(name ="rolename")
    private String roleName;

    @JSONField(serialize=false)
    @ManyToMany(targetEntity = SystemUser.class,fetch = FetchType.EAGER)
    @JoinTable(name = "systemUserRole",
            joinColumns = @JoinColumn(name = "systemRole_id", referencedColumnName = "id", nullable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "systemUser_id", referencedColumnName = "id", nullable = false, updatable = false))
    private Set<SystemUser> users = new HashSet<SystemUser>();


    @ManyToMany(targetEntity = SystemMenu.class)
    @JoinTable(name = "systemRoleMenu",
            joinColumns = @JoinColumn(name = "systemRole_id", referencedColumnName = "id", nullable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "systemMenu_id", referencedColumnName = "id", nullable = false, updatable = false))
    private  Set<SystemMenu> menus = new HashSet<SystemMenu>();

}
