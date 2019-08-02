package com.ibicn.hr.service.sys;

import com.ibicn.hr.entity.sys.OfficeArea;
import com.ibicn.hr.service.base.BaseServiceI;
import com.ibicn.hr.util.BaseModel;
import com.ibicn.hr.util.PageResult;

import java.util.List;

public interface BangongquServiceI extends BaseServiceI<OfficeArea> {
    void delete(Integer id);

    /**
     * 获取所有办公区
     *
     * @return
     */
    List<OfficeArea> getAllBangonqu();

    PageResult<OfficeArea> list(OfficeArea data, BaseModel asc);

    OfficeArea getById(Integer id);
}
