package com.ibicn.hr.entity.sys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户表
 **/
@Entity
@Getter
@Setter
@Table(name = "systemconfig")
public class SystemConfig implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "mkey")
    private String mKey;

    @Column(name = "mvalue", columnDefinition = "TEXT")
    private String mValue;
}