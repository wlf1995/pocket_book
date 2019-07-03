package com.ibicn.hr.service.impl.sys;

import com.ibicn.hr.bean.sys.SystemRole;
import com.ibicn.hr.dao.sys.SystemRoleDao;
import com.ibicn.hr.service.sys.SystemRoleServiceI;
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

@Transactional
@Service
public class SystemRoleServiceImpl implements SystemRoleServiceI {
    @Autowired
    SystemRoleDao systemRoleDao;

    @Override
    public Page list(SystemRole data, BaseModel baseModel){
        Pageable pageable = PageRequest.of(baseModel.getPage() - 1, baseModel.getLimit());
        Specification<SystemRole> specification = (Specification<SystemRole>) (root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            // 第一个userId为CloudServerDao中的字段，第二个userId为参数
            if (StringUtil.isNotEmpty(data.getName())) {
                Predicate p1 = criteriaBuilder.like(root.get("name"), data.getName());
                list.add(p1);
            }
            return criteriaBuilder.and(list.toArray(new Predicate[0]));
        };
        Page<SystemRole> all = systemRoleDao.findAll(specification, pageable);
        return all;
    }

    @Override
    public SystemRole getById(Integer id) {
        return systemRoleDao.getOne(id);
    }

    @Override
    public void save(SystemRole role) {
        systemRoleDao.save(role);
    }

    @Override
    public void update(SystemRole role) {
        systemRoleDao.save(role);
    }


    @Override
    public List<SystemRole> getAllRole() {
        List<SystemRole> list=systemRoleDao.findAll();
        if(CollectionUtil.size(list)>0){
            return list;
        }
        return Collections.emptyList();
    }
}
