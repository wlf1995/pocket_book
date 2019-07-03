package com.ibicn.hr.dao.sys;

import com.ibicn.hr.bean.sys.SystemRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemRoleDao extends JpaRepository<SystemRole, Integer>, JpaSpecificationExecutor<SystemRole> {

}
