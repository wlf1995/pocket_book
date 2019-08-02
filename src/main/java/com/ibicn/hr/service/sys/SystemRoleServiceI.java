package com.ibicn.hr.service.sys;

import com.ibicn.hr.entity.sys.systemRole;
import com.ibicn.hr.service.base.BaseServiceI;
import com.ibicn.hr.util.BaseModel;
import com.ibicn.hr.util.PageResult;

import java.util.List;

public interface SystemRoleServiceI extends BaseServiceI<systemRole> {
    /**
     * 获取所有橘色
     * @return
     */
    List<systemRole> getAllRole();

    PageResult list(systemRole data, BaseModel asc);

    systemRole getById(Integer id);
}
