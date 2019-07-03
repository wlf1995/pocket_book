package com.ibicn.hr.dao.sys;

import com.ibicn.hr.bean.sys.Bangongqu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BangongquDao extends JpaRepository<Bangongqu, Integer>, JpaSpecificationExecutor<Bangongqu> {

}
