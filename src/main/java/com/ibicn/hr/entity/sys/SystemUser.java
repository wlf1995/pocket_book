package com.ibicn.hr.entity.sys;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibicn.hr.ENUM.*;
import com.ibicn.hr.entity.personnel.Person;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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

    @ManyToOne
    @JoinColumn(name = "bangongquid")
    private Bangongqu bangongqu;
    //手机号
    @Column(name = "mobile")
    private String mobile;
    //性别
    @Column(name = "sex")
    private EnumSex sex;

    //出生日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "chushengriqi")
    private Date chushengRiqi;
    //学历
    @Column(name = "xueli")
    private EnumXueli xueli;

    //政治面貌
    @Column(name = "zhengzhimianmao")
    private EnumZhengzhiMianmao zhengzhiMianmao;

    //身份证号
    @Column(name = "idcard")
    private String IdCard;

    //入职时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "ruzhidate")
    private Date ruzhiDate;
    //离职时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "lizhidate")
    private Date lizhiDate;

    @Column(name = "creattime")
    private Date createdTime;
    @Column(name = "updatetime")
    private Date updateedTime;
    @Column(name = "updatepasswordday")
    private Date updatePassWordDay;
    //在职状态
    @Column(name = "zazhistatus")
    private EnumYesOrNo zazhiStatus;
    /**
     * 头像
     */
    @Column(name = "avatar")
    private String avatar;

    @Column(name = "userstatus")
    @Enumerated
    private EnumUserStatus userStatus;

    @Transient
    private String xueliIndex;
    @Transient
    private String sexIndex;
    @Transient
    private String zhengzhiMianmaoIndex;
    @Transient
    private String userStatusIndex;

    @Transient
    private String bangongquId;
    //所在部门
    @ManyToOne
    @JoinColumn(name = "deptid")
    private SystemDept dept;

    @Transient
    private String deptid;
    @ManyToMany(targetEntity = SystemRole.class)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "userid", referencedColumnName = "id", nullable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "roleid", referencedColumnName = "id", nullable = false, updatable = false))
    private Set<SystemRole> roles = new HashSet<SystemRole>();

    @ManyToOne
    @JoinColumn(name = "personid",nullable = false)
    private Person person;

    @JsonIgnore
    public void setUserStatus(String userStatus) {
        this.userStatus = (EnumUserStatus) EnumUtil.valueOf(EnumUserStatus.class, userStatus);
    }

    public void setUserStatusIndex(String userStatusIndex) {
        this.userStatusIndex = userStatusIndex;
        this.userStatus = (EnumUserStatus) EnumUtil.valueOf(EnumUserStatus.class, userStatusIndex);
    }

    public void setXueliIndex(String xueliIndex) {
        this.xueliIndex = xueliIndex;
        this.xueli = (EnumXueli) EnumUtil.valueOf(EnumXueli.class, xueliIndex);
    }

    public void setSexIndex(String sexIndex) {
        this.sexIndex = sexIndex;
        this.sex = (EnumSex) EnumUtil.valueOf(EnumSex.class, sexIndex);
    }

    public void setZhengzhiMianmaoIndex(String zhengzhiMianmaoIndex) {
        this.zhengzhiMianmaoIndex = zhengzhiMianmaoIndex;
        this.zhengzhiMianmao = (EnumZhengzhiMianmao) EnumUtil.valueOf(EnumZhengzhiMianmao.class, zhengzhiMianmaoIndex);
    }


}
