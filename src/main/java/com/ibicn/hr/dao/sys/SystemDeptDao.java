package com.ibicn.hr.dao.sys;

import com.ibicn.hr.dao.base.BaseDaoI;
import com.ibicn.hr.entity.sys.SystemDept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemDeptDao extends BaseDaoI<SystemDept> {

}
