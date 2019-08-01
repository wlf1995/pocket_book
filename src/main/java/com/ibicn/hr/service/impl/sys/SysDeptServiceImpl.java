package com.ibicn.hr.service.impl.sys;

import com.ibicn.hr.entity.sys.SystemDept;
import com.ibicn.hr.dao.sys.SystemDeptDao;
import com.ibicn.hr.service.sys.SystemDeptServiceI;
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
public class SysDeptServiceImpl implements SystemDeptServiceI {
    @Autowired
    SystemDeptDao systemDeptDao;

    @Override
    public Page list(SystemDept data, BaseModel baseModel){
        Pageable pageable = PageRequest.of(baseModel.getPage() - 1, baseModel.getLimit());
        Specification<SystemDept> specification = (Specification<SystemDept>) (root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            // 第一个userId为CloudServerDao中的字段，第二个userId为参数
            if (StringUtil.isNotEmpty(data.getName())) {
                Predicate p1 = criteriaBuilder.like(root.get("name"), data.getName());
                list.add(p1);
            }
            return criteriaBuilder.and(list.toArray(new Predicate[0]));
        };
        Page<SystemDept> all = systemDeptDao.findAll(specification, pageable);
        return all;
    }

    @Override
    public SystemDept getById(Integer id) {
        return systemDeptDao.getOne(id);
    }

    @Override
    public void save(SystemDept data) {
        systemDeptDao.save(data);
    }

    @Override
    public void update(SystemDept data) {
        systemDeptDao.save(data);
    }

    @Override
    public void delete(Integer id) {
        systemDeptDao.deleteById(id);
    }


    @Override
    public List<SystemDept> getAllBangonqu() {
        List<SystemDept> list=systemDeptDao.findAll();
        if(CollectionUtil.size(list)>0){
            return list;
        }
        return Collections.emptyList();
    }
}
