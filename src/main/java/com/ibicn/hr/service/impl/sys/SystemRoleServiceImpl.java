package com.ibicn.hr.service.impl.sys;

import com.ibicn.hr.dao.sys.SystemRoleDao;
import com.ibicn.hr.entity.sys.SystemRole;
import com.ibicn.hr.service.base.BaseServiceImpl;
import com.ibicn.hr.service.sys.SystemRoleServiceI;
import com.ibicn.hr.util.BaseModel;
import com.ibicn.hr.util.PageResult;
import com.ibicnCloud.util.CollectionUtil;
import com.ibicnCloud.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Transactional
@Service
public class SystemRoleServiceImpl extends BaseServiceImpl<SystemRole> implements SystemRoleServiceI {
    @Autowired
    SystemRoleDao systemRoleDao;

    @Autowired
    public SystemRoleServiceImpl(SystemRoleDao baseDao) {
        super(baseDao);
    }

    @Override
    public PageResult list(SystemRole data, BaseModel baseModel) {
        Specification<SystemRole> specification = (Specification<SystemRole>) (root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            // 第一个userId为CloudServerDao中的字段，第二个userId为参数
            if (StringUtil.isNotEmpty(data.getRoleName())) {
                Predicate p1 = criteriaBuilder.like(root.get("name"), "%" + data.getRoleName() + "%");
                list.add(p1);
            }
            return criteriaBuilder.and(list.toArray(new Predicate[0]));
        };
        PageResult pageResult = super.pageList(specification, baseModel);
        return pageResult;
    }

    @Override
    public SystemRole getById(Integer id) {
        return systemRoleDao.getOne(id);
    }

    @Override
    public List<SystemRole> getAllRole() {
        List<SystemRole> list = systemRoleDao.findAll();
        if (CollectionUtil.size(list) > 0) {
            return list;
        }
        return Collections.emptyList();
    }
}
