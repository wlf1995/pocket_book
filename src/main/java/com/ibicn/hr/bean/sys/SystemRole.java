package com.ibicn.hr.bean.sys;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name="Systemrole")
public class SystemRole implements Serializable {
    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name ="name")
    private String name;

    @Column(name ="createdtime")
    private Date createdTime;

    @JSONField(serialize=false)
    @ManyToMany(targetEntity = SystemUser.class)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "roleid", referencedColumnName = "id", nullable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "userid", referencedColumnName = "id", nullable = false, updatable = false))
    private Set<SystemUser> users = new HashSet<SystemUser>();


    @ManyToMany(targetEntity = SystemMenu.class, fetch = FetchType.EAGER)
    @JoinTable(name = "role_menu",
            joinColumns = @JoinColumn(name = "roleid", referencedColumnName = "id", nullable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "menuid", referencedColumnName = "id", nullable = false, updatable = false))
    private  Set<SystemMenu> menus = new HashSet<SystemMenu>();

}
