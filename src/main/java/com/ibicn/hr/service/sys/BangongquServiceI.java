package com.ibicn.hr.service.sys;

import com.ibicn.hr.entity.sys.Bangongqu;
import com.ibicn.hr.service.base.BaseServiceI;
import com.ibicn.hr.util.BaseModel;
import com.ibicn.hr.util.PageResult;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BangongquServiceI extends BaseServiceI<Bangongqu> {
    void delete(Integer id);

    /**
     * 获取所有办公区
     *
     * @return
     */
    List<Bangongqu> getAllBangonqu();

    PageResult<Bangongqu> list(Bangongqu data, BaseModel asc);

    Bangongqu getById(Integer id);
}
