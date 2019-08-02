package com.ibicn.hr.entity.sys;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibicn.hr.ENUM.*;
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
public class systemUser implements Serializable {

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

    /**
     *0为停用状态，1为正常状态
     */
    @Column(name = "status")
    @Enumerated
    private EnumUserStatus status;

    /**
     * 创建的时间
     */
    @Column(name = "creattime")
    private Date createdTime;

    /**
     * 最后修改的时间
     */
    @Column(name = "updatetime")
    private Date updateedTime;

    /**
     * 修改的次数
     */
    @Column(name = "updatedcount")
    private Integer updatedCount;


    @ManyToMany(targetEntity = systemRole.class, fetch = FetchType.EAGER)
    @JoinTable(name = "systemUserRole",
            joinColumns = @JoinColumn(name = "systemUser_id", referencedColumnName = "id", nullable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "systemRole_id", referencedColumnName = "id", nullable = false, updatable = false))
    private Set<systemRole> roles = new HashSet<systemRole>();

    @JsonIgnore
    public void setStatus(String userStatus) {
        this.status = (EnumUserStatus) EnumUtil.valueOf(EnumUserStatus.class, userStatus);
    }
    public void setUserStatusIndex(String userStatusIndex) {
        this.userStatusIndex = userStatusIndex;
        this.status = (EnumUserStatus) EnumUtil.valueOf(EnumUserStatus.class, userStatusIndex);
    }

    @Transient
    private String userStatusIndex;

}
