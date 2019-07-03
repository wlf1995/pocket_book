package com.ibicn.hr.service.sys;

import com.ibicn.hr.bean.sys.Bangongqu;
import com.ibicn.hr.bean.sys.SystemRole;
import com.ibicn.hr.util.BaseModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BangongquServiceI {
    void delete(Integer id);

    /**
     * 获取所有办公区
     * @return
     */
    List<Bangongqu> getAllBangonqu();

    Page<Bangongqu> list(Bangongqu data, BaseModel asc);

    Bangongqu getById(Integer id);

    void save(Bangongqu role);

    void update(Bangongqu role);
}
