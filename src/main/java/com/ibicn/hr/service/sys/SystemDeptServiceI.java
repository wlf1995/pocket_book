package com.ibicn.hr.service.sys;

import com.ibicn.hr.entity.sys.department;
import com.ibicn.hr.service.base.BaseServiceI;
import com.ibicn.hr.util.BaseModel;
import com.ibicn.hr.util.PageResult;

import java.util.List;

public interface SystemDeptServiceI extends BaseServiceI<department> {

    List<department> getAllBangonqu();

    PageResult list(department data, BaseModel asc);

    department getById(Integer id);

}
