package com.ibicn.hr.entity.sys;


import com.alibaba.fastjson.annotation.JSONField;
import com.ibicn.hr.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="systemRole")
public class systemRole extends BaseEntity implements Serializable {

    /**
     * 主键
     */
    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 角色的名称
     */
    @Column(name ="rolename")
    private String roleName;


    @JSONField(serialize=false)
    @ManyToMany(targetEntity = systemUser.class,fetch = FetchType.EAGER)
    @JoinTable(name = "systemUserRole",
            joinColumns = @JoinColumn(name = "systemRole_id", referencedColumnName = "id", nullable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "systemUser_id", referencedColumnName = "id", nullable = false, updatable = false))
    private Set<systemUser> users = new HashSet<systemUser>();


    @ManyToMany(targetEntity = systemMenu.class)
    @JoinTable(name = "systemRoleMenu",
            joinColumns = @JoinColumn(name = "systemRole_id", referencedColumnName = "id", nullable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "systemMenu_id", referencedColumnName = "id", nullable = false, updatable = false))
    private  Set<systemMenu> menus = new HashSet<systemMenu>();

}
