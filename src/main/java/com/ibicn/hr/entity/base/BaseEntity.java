package com.ibicn.hr.entity.base;

import com.ibicn.hr.ENUM.EnumBaseStatus;
import com.ibicn.hr.ENUM.EnumUtil;
import com.ibicn.hr.ENUM.EnumXueli;
import com.ibicn.hr.entity.sys.SystemUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    /**
     * 主键
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * 创建时间
     **/
    @CreatedDate
    @Column(name = "createdtime",nullable = false)
    private Date createdTime;
    /**
     * 修改时间
     **/
    @LastModifiedDate
    @Column(name = "updatedtime",nullable = false)
    private Date updatedTime;
    /**
     * 修改次数
     **/
    @Column(name = "updatedcount",nullable = false)
    private Date updatedCount;
    /**
     * 状态 0为停用状态，1为正常状态,默认为正常状态
     **/
    @Column(name = "status",nullable = false)
    private EnumBaseStatus status=EnumBaseStatus.正常;

    /**
     * 添加人
     **/
    @CreatedBy
    @Column(name = "createuserid")
    private Integer createUser;

    /**
     * 最后修改人
     **/
    @LastModifiedBy
    @Column(name = "lastmodifieduserid")
    private Integer LastModifiedUser;
    /**
     * status字段的枚举适配类
     **/
    @Transient
    private String statusIndex;

    public void setStatusIndex(String statusIndex) {
        this.statusIndex = statusIndex;
        this.status = (EnumBaseStatus) EnumUtil.valueOf(EnumBaseStatus.class, statusIndex);
    }
}
