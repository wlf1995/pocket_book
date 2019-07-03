package com.ibicn.hr.bean.sys;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户表
 **/
@Entity
@Data
@Table(name = "systemconfig")
public class SystemConfig implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "mkey")
    private String mKey;

    @Column(name = "mvalue", columnDefinition = "TEXT")
    private String mValue;
}
