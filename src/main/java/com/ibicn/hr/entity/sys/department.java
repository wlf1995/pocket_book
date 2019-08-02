package com.ibicn.hr.entity.sys;

import com.ibicn.hr.ENUM.EnumBaseStatus;
import com.ibicn.hr.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "department")
public class department extends BaseEntity implements Serializable  {

    /**
     * 主键
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 部门名称
     */
    @Column(name = "departmentname")
    private String departmentName;

    /**
     * 排序号
     */
    @Column(name = "sortnumber")
    private Integer sortNumber;

    /**
     * 上级部门的外键
     */
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private department parent_id;

    /**
     * 0为停用状态，1为正常状态
     */
    @Column(name = "status")
    private EnumBaseStatus status;


    /**
     * 办公区的外键
     */
    @ManyToOne
    @JoinColumn(name = "officearea_id")
    private officeArea officeArea_id;

}
