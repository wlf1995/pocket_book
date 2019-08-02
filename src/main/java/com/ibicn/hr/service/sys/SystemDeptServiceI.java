package com.ibicn.hr.service.sys;

import com.ibicn.hr.entity.sys.Department;
import com.ibicn.hr.service.base.BaseServiceI;
import com.ibicn.hr.util.BaseModel;
import com.ibicn.hr.util.PageResult;

import java.util.List;

public interface SystemDeptServiceI extends BaseServiceI<Department> {

    List<Department> getAllBangonqu();

    PageResult list(Department data, BaseModel asc);

    Department getById(Integer id);

}
