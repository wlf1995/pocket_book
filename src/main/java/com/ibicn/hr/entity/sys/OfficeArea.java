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
@Table(name = "officeArea")
public class OfficeArea extends BaseEntity implements Serializable {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 办公区名
     */
    @Column(name = "areaname")
    private String areaName;

    /**
     * 编号
     */
    @Column(name = "bianhao")
    private String bianhao;

    /**
     * 办公区的地址位置
     */
    @Column(name = "address")
    private String address;
}
