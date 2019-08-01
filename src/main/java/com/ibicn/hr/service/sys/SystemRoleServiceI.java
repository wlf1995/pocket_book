package com.ibicn.hr.service.sys;

import com.ibicn.hr.entity.sys.SystemRole;
import com.ibicn.hr.util.BaseModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SystemRoleServiceI   {
    /**
     * 获取所有橘色
     * @return
     */
    List<SystemRole> getAllRole();

    Page<SystemRole> list(SystemRole data, BaseModel asc);

    SystemRole getById(Integer id);

    void save(SystemRole role);

    void update(SystemRole role);
}
