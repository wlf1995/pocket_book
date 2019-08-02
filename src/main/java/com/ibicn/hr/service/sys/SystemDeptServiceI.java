package com.ibicn.hr.service.sys;

import com.ibicn.hr.entity.sys.Department;
import com.ibicn.hr.service.base.BaseServiceI;

import java.util.List;

public interface SystemDeptServiceI extends BaseServiceI<Department> {

    List<Department> getAllBangonqu();


    Department getById(Integer id);

}
