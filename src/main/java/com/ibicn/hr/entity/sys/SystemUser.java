package com.ibicn.hr.entity.sys;

import com.alibaba.fastjson.annotation.JSONField;
import com.ibicn.hr.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "SystemUser")
public class SystemUser extends BaseEntity implements Serializable {
    /**
     * 登录系统的用户名
     */
    @NotNull
    @Column(name = "username",nullable = false)
    private String userName;

    /**
     * 登录系统的密码
     */
    @JSONField(serialize = false)
    @Column(name = "password",nullable = false)
    private String password;

    /**
     * 登录人的真实名字
     */
    @Column(name = "realname",nullable = false)
    private String realName;

    @ManyToMany(targetEntity = SystemRole.class, fetch = FetchType.EAGER)
    @JoinTable(name = "systemUserRole",
            joinColumns = @JoinColumn(name = "systemUser_id", referencedColumnName = "id", nullable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "systemRole_id", referencedColumnName = "id", nullable = false, updatable = false))
    private Set<SystemRole> roles = new HashSet<SystemRole>();
}
