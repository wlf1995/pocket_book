package com.ibicn.hr.bean.sys;

import com.ibicn.hr.ENUM.EnumBaseStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "systemdept")
public class SystemDept implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "userid")
    private SystemUser sysUser;

    @ManyToOne
    @JoinColumn(name = "parentdept")
    private SystemDept parentDept;

    //其他辅助字段 包括 创建时间 修改时间 修改次数 状态  每个类都有
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "createdTime")
    private Date createdTime;

    /**
     * 修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "modifiedTime")
    private Date modifiedTime;

    /**
     * 修改次数
     */
    @Column(name = "modifiedCount")
    private int modifiedCount;

    /**
     * 状态
     */
    @Column(name = "status")
    private EnumBaseStatus status;

}
