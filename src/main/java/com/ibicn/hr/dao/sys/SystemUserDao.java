package com.ibicn.hr.dao.sys;

import com.ibicn.hr.dao.base.BaseDaoI;
import com.ibicn.hr.entity.sys.SystemUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemUserDao extends BaseDaoI<SystemUser> {
    @Query(value = "select DATEDIFF(updatePassWordDay,NOW()) as datenum from systemuser where id = ?1", nativeQuery = true)
    List<Object> getUpdatePassWordDay(int systemUser_id);

    SystemUser findByUserName(String userName);
}
