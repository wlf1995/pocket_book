package com.ibicn.hr.service.sys;

import com.ibicn.hr.entity.sys.SystemRole;
import com.ibicn.hr.service.base.BaseServiceI;
import com.ibicn.hr.util.BaseModel;
import com.ibicn.hr.util.PageResult;

import java.util.List;

public interface SystemRoleServiceI extends BaseServiceI<SystemRole> {
    /**
     * 获取所有橘色
     * @return
     */
    List<SystemRole> getAllRole();

    SystemRole getById(Integer id);
}
