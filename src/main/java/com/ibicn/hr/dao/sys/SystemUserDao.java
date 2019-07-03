package com.ibicn.hr.dao.sys;

import com.ibicn.hr.bean.sys.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemUserDao extends JpaRepository<SystemUser, Integer>, JpaSpecificationExecutor<SystemUser> {
    @Query(value = "select DATEDIFF(updatePassWordDay,NOW()) as datenum from systemuser where id = ?1",nativeQuery = true )
    List<Object> getUpdatePassWordDay(int systemUser_id);
}
