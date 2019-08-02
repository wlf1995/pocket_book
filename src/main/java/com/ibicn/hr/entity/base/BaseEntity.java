package com.ibicn.hr.entity.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
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
}
