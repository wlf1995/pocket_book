package com.ibicn.hr.service.sys;

import com.ibicn.hr.entity.sys.SystemDept;
import com.ibicn.hr.service.base.BaseServiceI;
import com.ibicn.hr.service.base.BaseServiceImpl;
import com.ibicn.hr.util.BaseModel;
import com.ibicn.hr.util.PageResult;

import java.util.List;

public interface SystemDeptServiceI extends BaseServiceI<SystemDept> {

    List<SystemDept> getAllBangonqu();

    PageResult list(SystemDept data, BaseModel asc);

    SystemDept getById(Integer id);

}
