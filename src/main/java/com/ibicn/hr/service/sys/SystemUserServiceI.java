package com.ibicn.hr.service.sys;


import com.ibicn.hr.bean.sys.SystemUser;
import com.ibicn.hr.util.BaseModel;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * The Interface UserService.
 */
public interface SystemUserServiceI {

    SystemUser findByUserName(String userName);

    List<SystemUser> getSystemUserByName(String name, int id);

    SystemUser getSystemUserByBianhao(String bianhao);

    /**
     * 获取修改密码天数
     *
     * @param systemUser_id
     * @return
     */
    int getUpdatePassWordDay(int systemUser_id);

    /**
     * 根据id或者name 获得用户
     *
     * @param name
     * @param id
     * @return
     */
    List<SystemUser> getUser(String name, int id);


    List<SystemUser> getByCompany(Integer companyId);

    List<String> getUserBianhaoByIds(String ids);

    /**
     * @return com.ibicn.hr.bean.sys.SystemUser
     * @Description 编辑用户时验证用户名，编号是不是重复
     * @Date 11:38 2019/2/22
     * @Param userName
     * @Param userBianhao
     * @Param id
     **/
    SystemUser getUsesByNameAndBianhaoNoId(String userName, String userBianhao, int id);

    Page<SystemUser> list(SystemUser data, BaseModel baseModel);

    void save(SystemUser user);

    void update(SystemUser user);

    SystemUser getById(Integer id);

    /**
     * @param
     * @return java.util.HashMap<java.lang.String, java.lang.Object>
     * @Description 获取在职离职人员统计
     * @Date 0:42 2019/7/4
     **/
    HashMap<String, Object> getRLzhi(Integer deptid,String beginDate,String endDate);

    /**
     *根据部门统计在职离职人数
     * @param beginDate
     * @param endDate
     */
    HashMap<String, Object> getRLzhiByDept(String beginDate, String endDate);
}
