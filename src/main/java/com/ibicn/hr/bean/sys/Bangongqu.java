package com.ibicn.hr.bean.sys;

import com.ibicn.hr.ENUM.EnumBaseStatus;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "bangongqu")
public class Bangongqu {
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
    @Column(name = "name")
    private String name;

    /**
     * 编号
     */
    @Column(name = "bianhao")
    private String bianhao;

    /**
     * 地址
     */
    @Column(name = "address")
    private String address;

    /**
     * 所在城市
     */
    @Column(name = "cityName")
    private String cityName;


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
