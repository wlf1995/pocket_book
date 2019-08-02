package com.ibicn.hr.entity.personnel;

import com.ibicn.hr.ENUM.EnumSex;
import com.ibicn.hr.ENUM.EnumXueli;
import com.ibicn.hr.entity.base.BaseEntity;
import com.ibicn.hr.entity.sys.Department;
import com.ibicn.hr.entity.sys.SystemUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @Author 王立方
 * @Description 人事档案表
 * @Date 9:13 2019/8/2
 **/
@Getter
@Setter
@Entity
@Table(name = "person")
public class Person extends BaseEntity implements Serializable {

    /**
     * 档案编号	personNumber	varchar(50)		纸质档案的编号，不能重复
     **/
    @Column(name = "personnumber", nullable = false)
    private String personNumber;

    /**
     * 姓名	name	varchar(50)		人员的姓名
     **/
    @Column(name = "name", nullable = false)
    private String name;


    /**
     * 性别	sex	int		0为女，1为男
     **/
    @Column(name = "sex", nullable = false)
    private EnumSex sex;

    /**
     * 出生日期	birthday	dateTime		出生日期
     **/
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birthday", nullable = false)
    private Date birthday;

    /**
     * 籍贯	native	varchar(50)		籍贯，比如北京市海淀区
     * native 为java 关键字,所以在后面加_
     **/
    @Column(name = "native", nullable = false)
    private String native_;


    /**
     * 身份证号	idnumber	varchar(18)		身份证号，唯一验证
     **/
    @Column(name = "idnumber", nullable = false)
    private String idnumber;

    /**
     * 发证机关	licensingAuthority	varchar(30)		身份证的发证机关
     **/
    @Column(name = "licensingauthority")
    private String licensingAuthority;
    /**
     * 身份证有效期截止日	expirydate	datetime		身份证的有效期
     **/
    @Column(name = "expirydate")
    private Date expirydate;

    /**
     * 身份证地址	address	varchar(200)		身份证的地址
     **/
    @Column(name = "address")
    private String address;
    /**
     * 现住址	addressnow	varchar(200)		现在的居住地址
     **/
    @Column(name = "addressnow")
    private String addressnow;

    /**
     * 学历	qualifications	varchar(30)		学历，比如本科、大专、研究生
     **/
    @Column(name = "qualifications")
    private EnumXueli qualifications;

    /**
     * 毕业院校	university	varchar(50)		毕业院校
     **/
    @Column(name = "university")
    private String university;
    /**
     * 专业	speciality	varchar(50)		学习的专业
     **/
    @Column(name = "speciality")
    private String speciality;

    /**
     * 联系手机	mobile	varchar(11)		联系手机
     **/
    @Column(name = "mobile")
    private String mobile;

    /**
     * 联系邮箱	email	varchar(100)		联系邮箱
     **/
    @Column(name = "email")
    private String email;

    /**
     * 工作职务	jobTitle	varchar(18)		工作的职务，比如是总监、经理
     **/
    @Column(name = "jobtitle")
    private String jobTitle;

    /**
     * 入职时间	workDate	datetime		入职的日期
     **/
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "workdate", nullable = false)
    private Date workDate;

    /**
     * 离职时间	leaveDate	datetime		离职的日期
     **/
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "leavedate")
    private Date leaveDate;
    //------------关联关系---------------//
    /**
     * 所在部门	department_id	int	外键	部门表的外键
     **/
    @ManyToOne
    @JoinColumn(name = "department_id",nullable = false)
    private Department department_id;

    /**
     * 对应的扫描文件
     * 由多的一方维护关系
     **/
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "person")
    private Set<SystemUser> systemUserSet;

    /**
     * 对应的扫描文件
     **/
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "person")
    private Set<Attachment> attachmentSet;

    /**
     * 对应的人事档案公司变动履历表
     **/
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "person")
    private Set<CompanyCV> companyCVSet;

    /**
     * 对应的学习和工作简历表
     **/
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "person")
    private Set<CV> cvSet;

    /**
     * 对应的家庭成员表
     **/
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "person")
    private Set<Family> familySet;
}
