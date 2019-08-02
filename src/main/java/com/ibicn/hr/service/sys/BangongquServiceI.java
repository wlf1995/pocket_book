package com.ibicn.hr.service.sys;

import com.ibicn.hr.entity.sys.officeArea;
import com.ibicn.hr.service.base.BaseServiceI;
import com.ibicn.hr.util.BaseModel;
import com.ibicn.hr.util.PageResult;

import java.util.List;

public interface BangongquServiceI extends BaseServiceI<officeArea> {
    void delete(Integer id);

    /**
     * 获取所有办公区
     *
     * @return
     */
    List<officeArea> getAllBangonqu();

    PageResult<officeArea> list(officeArea data, BaseModel asc);

    officeArea getById(Integer id);
}
