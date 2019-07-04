package com.ibicn.hr.service.sys;

import com.ibicn.hr.bean.sys.Bangongqu;
import com.ibicn.hr.bean.sys.SystemDept;
import com.ibicn.hr.util.BaseModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SystemDeptServiceI {
    void delete(Integer id);

    List<SystemDept> getAllBangonqu();

    Page<SystemDept> list(SystemDept data, BaseModel asc);

    SystemDept getById(Integer id);

    void save(SystemDept role);

    void update(SystemDept role);
}
