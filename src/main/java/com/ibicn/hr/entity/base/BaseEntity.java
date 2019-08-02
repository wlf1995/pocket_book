package com.ibicn.hr.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibicn.hr.ENUM.EnumBaseStatus;
import com.ibicn.hr.ENUM.EnumUserStatus;
import com.ibicn.hr.ENUM.EnumUtil;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.util.Date;
@Getter
@Setter
public class BaseEntity {
    /**
     * 创建时间
     **/
    @Column(name = "createdtime")
    private Date createdTime;
    /**
     * 修改时间
     **/
    @Column(name = "updatedtime")
    private Date updatedTime;
    /**
     * 修改次数
     **/
    @Column(name = "updatedcount")
    private Date updatedCount;
    /**
     * 状态 0为停用状态，1为正常状态
     **/
    @Column(name = "status")
    private EnumBaseStatus status;


    @Transient
    private String userStatusIndex;

    @JsonIgnore
    public void setStatus(String userStatus) {
        this.status = (EnumBaseStatus) EnumUtil.valueOf(EnumBaseStatus.class, userStatus);
    }
    public void setUserStatusIndex(String userStatusIndex) {
        this.status = (EnumBaseStatus) EnumUtil.valueOf(EnumBaseStatus.class, userStatusIndex);
    }

}
