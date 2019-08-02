package com.ibicn.hr.entity.personnel;

import com.ibicn.hr.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author 王立方
 * @Description 简历表-学习和工作简历表
 * @Date 9:13 2019/8/2
 **/
@Getter
@Setter
@Entity
@Table(name = "cv")
public class CV extends BaseEntity implements Serializable {

    /**
     * 时间-简历开始时间
     **/
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "begincvdate")
    private Date begincvDate;
    /**
     * 时间-简历结束时间
     **/
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "endcvdate")
    private Date endcvDate;
    /**
     * 单位名称-学习或者工作单位的名称
     **/
    @Column(name = "companyname")
    private String companyName;
    /**
     * 主要工作 -主要从事工作内容
     **/
    @Column(name = "job")
    private String job;

    /**
     * 职务-职务
     **/
    @Column(name = "jobtitle")
    private String jobTitle;

    @ManyToOne
    @JoinColumn(name="person_id",nullable = false)
    private Person person;
}
