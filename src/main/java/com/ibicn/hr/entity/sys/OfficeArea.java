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
    @Column(name = "areaname",nullable = false)
    private String areaName;


    /**
     * 办公区的地址位置
     */
    @Column(name = "address",nullable = false)
    private String address;


    /**
     * 经度
     */
    @Column(name = "longitude")
    private String longitude;
    /**
     * 纬度
     */
    @Column(name = "latitude")
    private String latitude;

}
