package com.ibicn.hr.entity.sys;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibicn.hr.ENUM.*;
import com.ibicn.hr.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "systemUser")
public class systemUser extends BaseEntity implements Serializable {

    /**
     * 主键
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 登录系统的用户名
     */
    @NotNull
    @Column(name = "username")
    private String userName;

    /**
     * 登录系统的密码
     */
    @JSONField(serialize = false)
    @Column(name = "password")
    private String password;

    /**
     * 登录人的真实名字
     */
    @Column(name = "realname")
    private String realName;



    @ManyToMany(targetEntity = systemRole.class, fetch = FetchType.EAGER)
    @JoinTable(name = "systemUserRole",
            joinColumns = @JoinColumn(name = "systemUser_id", referencedColumnName = "id", nullable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "systemRole_id", referencedColumnName = "id", nullable = false, updatable = false))
    private Set<systemRole> roles = new HashSet<systemRole>();



}
