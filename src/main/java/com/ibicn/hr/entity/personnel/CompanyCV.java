package com.ibicn.hr.entity.personnel;

import com.ibicn.hr.ENUM.EnumLevel;
import com.ibicn.hr.ENUM.EnumUtil;
import com.ibicn.hr.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author 王立方
 * @Description 履历表-人事档案公司变动履历表
 * @Date 9:13 2019/8/2
 **/
@Getter
@Setter
@Entity
@Table(name = "companycv")
public class CompanyCV extends BaseEntity implements Serializable {

    /**
     * 变动时间
     **/
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "cvdate",nullable = false)
    private Date cvDate;
    /**
     * 变动原由
     **/
    @Column(name = "reason",nullable = false)
    private String reason;
    /**
     * 变动的内容
     **/
    @Column(name = "content",nullable = false)
    private String content;
    /**
     * 奖惩级别
     * -3代表“惩3”，-2代表“惩2”，-1代表“惩1”，0代表“无奖惩”，1代表“奖1”，2代表“奖2”，3代表“奖3”
     **/
    @Column(name = "level",nullable = false)
    private EnumLevel level;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;
    /**
     * 奖惩级别枚举适配字段
     **/
    @Transient
    private String levelIndex;

    public void setLevelIndex(String levelIndex) {
        this.levelIndex = levelIndex;
        this.level = (EnumLevel) EnumUtil.valueOf(EnumLevel.class, levelIndex);
    }
}

