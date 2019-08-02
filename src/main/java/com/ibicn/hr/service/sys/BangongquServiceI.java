package com.ibicn.hr.service.sys;

import com.ibicn.hr.entity.sys.OfficeArea;
import com.ibicn.hr.service.base.BaseServiceI;

import java.util.List;

public interface BangongquServiceI extends BaseServiceI<OfficeArea> {
    /**
     * 获取所有办公区
     *
     * @return
     */
    List<OfficeArea> getAllBangonqu();

}
