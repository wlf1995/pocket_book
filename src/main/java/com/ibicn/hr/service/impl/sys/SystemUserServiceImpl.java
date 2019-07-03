package com.ibicn.hr.service.impl.sys;

import com.ibicn.hr.bean.sys.SystemUser;
import com.ibicn.hr.dao.sys.SystemUserDao;
import com.ibicn.hr.service.sys.SystemUserServiceI;
import com.ibicn.hr.util.BaseModel;
import com.ibicnCloud.util.CollectionUtil;
import com.ibicnCloud.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author abel
 * @ClassName UserServiceImpl
 */
@Service
@Transactional
public class SystemUserServiceImpl implements SystemUserServiceI {
    @Autowired
    SystemUserDao systemUserDao;

    @Override
    public SystemUser findByUserName(String username) {
        Specification<SystemUser> specification = (Specification<SystemUser>) (root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if(StringUtil.isNotEmpty(username)){
                Predicate p2 = criteriaBuilder.like(root.get("userName"), username);
                list.add(p2);
            }
            return criteriaBuilder.and(list.toArray(new Predicate[0]));
        };
        List<SystemUser> list = systemUserDao.findAll(specification);
        if (CollectionUtil.size(list) == 0) {
            return null;
        } else {
            return list.get(0);
        }
    }

	@Override
	public List<SystemUser> getSystemUserByName(String name,int id) {
        Specification<SystemUser> specification = (Specification<SystemUser>) (root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if(StringUtil.isNotEmpty(name)){
                Predicate p2 = criteriaBuilder.like(root.get("realName"), name);
                list.add(p2);
            }
            if (id > 0) {
                Predicate p1 = criteriaBuilder.equal(root.get("id"), id);
                list.add(p1);
            }
            return criteriaBuilder.and(list.toArray(new Predicate[0]));
        };
        List<SystemUser> list = systemUserDao.findAll(specification);
        if (CollectionUtil.size(list) > 0) {
            return list;
        }
        return Collections.emptyList();
    }

    @Override
    public Page<SystemUser> list(SystemUser data, BaseModel baseModel) {
        Pageable pageable = PageRequest.of(baseModel.getPage() - 1, baseModel.getLimit());
        Specification<SystemUser> specification = (Specification<SystemUser>) (root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();

            if(StringUtil.isNotEmpty(data.getRealName())){
                Predicate p2 = criteriaBuilder.like(root.get("realName"), data.getRealName());
                list.add(p2);
            }
            return criteriaBuilder.and(list.toArray(new Predicate[0]));
        };
        Page<SystemUser> all = systemUserDao.findAll(specification, pageable);
        return all;
    }

    @Override
    public void save(SystemUser user) {
        systemUserDao.save(user);
    }

    @Override
    public void update(SystemUser user) {
        systemUserDao.save(user);
    }

    @Override
    public SystemUser getById(Integer id) {
        return systemUserDao.getOne(id);
    }

    @Override
    public SystemUser getSystemUserByBianhao(String bianhao) {
        Specification<SystemUser> specification = (Specification<SystemUser>) (root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if(StringUtil.isNotEmpty(bianhao)){
                Predicate p2 = criteriaBuilder.like(root.get("userBianhao"), bianhao);
                list.add(p2);
            }
            return criteriaBuilder.and(list.toArray(new Predicate[0]));
        };
        List<SystemUser> list =systemUserDao.findAll(specification);
        if (CollectionUtil.size(list) > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public int getUpdatePassWordDay(int systemUser_id) {
        List<Object> result = systemUserDao.getUpdatePassWordDay(systemUser_id);
        if (result != null && CollectionUtil.size(result) > 0) {
            if (result.get(0) != null) {
                String date = result.get(0).toString();
                return StringUtil.parseInt(date);
            } else {
                return -61;
            }
        }
        return 0;
    }

    @Override
    public List<SystemUser> getUser(String name, int id) {
        Specification<SystemUser> specification = (Specification<SystemUser>) (root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if(StringUtil.isNotEmpty(name)){
                Predicate p2 = criteriaBuilder.like(root.get("realName"), name);
                list.add(p2);
            }
            if (id > 0) {
                Predicate p1 = criteriaBuilder.equal(root.get("id"), id);
                list.add(p1);
            }
            return criteriaBuilder.and(list.toArray(new Predicate[0]));
        };
        List<SystemUser> list = systemUserDao.findAll(specification);
        if (CollectionUtil.size(list) > 0) {
            return list;
        }
        return Collections.emptyList();
    }


    @Override
    public List<SystemUser> getByCompany(Integer companyId) {
//        String hql = "from SystemUser where ? in elements (companys)";
//        List<ListValues> values = new ArrayList<>();
//        values.add(new ListValues(companyId, "int"));
//        List<SystemUser> list = baseDaoUser.find(hql, values, -1, -1);
//        if (CollectionUtil.size(list) > 0) {
//            return list;
//        }
        return Collections.emptyList();
    }

    /**
     * 根据用户id串，获得这些人的userBianhao
     */
    @Override
    public List<String> getUserBianhaoByIds(String ids) {
        String[] split = ids.split(",");
        List<Integer> list1 =new ArrayList<>();
        for (int i=0;i<split.length;i++){
            if(StringUtil.isEmpty(split[i])){
                continue;
            }
            list1.add(StringUtil.parseInt(split[i]));
        }

        List<SystemUser> list = systemUserDao.findAllById(list1);
        List<String> bianhaoList=new ArrayList<>();

        if (CollectionUtil.size(list) > 0) {
            for (SystemUser user:list){
                bianhaoList.add(user.getUserBianhao());
            }
            return bianhaoList;
        }
        return new ArrayList<String>();
    }

    /**
     * @Author 田华健
     * @Description 编辑用户时验证用户名，编号是不是重复
     * @Date 11:38 2019/2/22
     * @Param userName
     * @Param userBianhao
     * @Param id
     * @return com.ibicn.bean.base.SystemUser
     **/
    @Override
    public SystemUser getUsesByNameAndBianhaoNoId(String userName, String userBianhao, int id) {
        Specification<SystemUser> specification = (Specification<SystemUser>) (root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if(id != 0){
                Predicate p1 = criteriaBuilder.notEqual(root.get("id"), id);
                list.add(p1);
            }
            if(StringUtil.isNotBlank(userName)){
                Predicate p2 = criteriaBuilder.equal(root.get("userName"), userName);
                list.add(p2);
            }
            if(StringUtil.isNotBlank(userBianhao)){
                Predicate p3 = criteriaBuilder.equal(root.get("userBianhao"), userBianhao);
                list.add(p3);
            }
            return criteriaBuilder.and(list.toArray(new Predicate[0]));
        };
        List<SystemUser> list = systemUserDao.findAll(specification);
        if (CollectionUtil.size(list) > 0) {
            return list.get(0);
        }
        return null;
    }
}
