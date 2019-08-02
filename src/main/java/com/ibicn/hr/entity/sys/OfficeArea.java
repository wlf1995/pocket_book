package com.ibicn.hr.entity.sys;

import com.ibicn.hr.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "officeArea")
public class OfficeArea extends BaseEntity implements Serializable {

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
