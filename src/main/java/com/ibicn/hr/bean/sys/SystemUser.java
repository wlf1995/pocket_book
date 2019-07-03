package com.ibicn.hr.bean.sys;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibicn.hr.ENUM.EnumUserStatus;
import com.ibicn.hr.ENUM.EnumUtil;
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
@Table(name = "Systemuser")
public class SystemUser implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "userbianhao")
    private String userBianhao;

    @NotNull
    @Column(name = "username")
    private String userName;

    @JSONField(serialize = false)
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;

    @Column(name = "regtime")
    private Date regTime;

    @Column(name = "realname")
    private String realName;

    @Column(name = "creattime")
    private Date createdTime;
    @Column(name = "updatetime")
    private Date updateedTime;
    @Column(name = "updatepasswordday")
    private Date updatePassWordDay;

    /**
     * 微信头像
     */
    @Column(name = "avatar")
    private String avatar;

    @Column(name = "userstatus")
    @Enumerated
    private EnumUserStatus userStatus;

    @Transient
    private String userStatusIndex;

    @Transient
    private String projectIds;
    /**
     * type为1代表微信登陆
     */
    @Column(name = "type")
    private Integer type;

    @ManyToMany(targetEntity = SystemRole.class, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "userid", referencedColumnName = "id", nullable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "roleid", referencedColumnName = "id", nullable = false, updatable = false))
    private Set<SystemRole> roles = new HashSet<SystemRole>();

    @JsonIgnore
    public void setUserStatus(String userStatus) {
        this.userStatus = (EnumUserStatus) EnumUtil.valueOf(EnumUserStatus.class, userStatus);
    }
    public void setUserStatusIndex(String userStatusIndex) {
        this.userStatusIndex = userStatusIndex;
        this.userStatus = (EnumUserStatus) EnumUtil.valueOf(EnumUserStatus.class, userStatusIndex);
    }

    /**
     * 判断是否是管理组中的人员
     * @return
     */
    public boolean IsAdmin() {
        for (SystemRole systemRole : roles) {
            if (systemRole.getName().indexOf("管理") != -1) {
                return true;
            }
        }
        return false;
    }

}
