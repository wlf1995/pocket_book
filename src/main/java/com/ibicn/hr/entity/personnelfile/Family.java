package com.ibicn.hr.entity.personnelfile;

import com.ibicn.hr.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author 王立方
 * @Description 家庭成员表
 * @Date 9:13 2019/8/2
 **/
@Getter
@Setter
@Entity
@Table(name = "family")
public class Family  extends BaseEntity implements Serializable {
    /**
     * 主键
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    /**
     * 成员姓名-成员姓名
     **/
    @Column(name = "name")
    private String name;
    /**
     * 与本人关系-比如 父亲、母亲
     **/
    @Column(name = "relation")
    private String relation;
    /**
     * 工作单位-工作单位
     **/
    @Column(name = "company")
    private String company;
    /**
     * 职务-职务
     **/
    @Column(name = "jobtitle")
    private String jobTitle;
    /**
     * 联系手机-家庭成员的联系手机
     **/
    @Column(name = "mobile")
    private String mobile;


}
