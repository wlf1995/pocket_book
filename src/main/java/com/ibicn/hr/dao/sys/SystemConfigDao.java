package com.ibicn.hr.dao.sys;

import com.ibicn.hr.entity.sys.SystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemConfigDao extends JpaRepository<SystemConfig, Integer>, JpaSpecificationExecutor<SystemConfig> {

}
