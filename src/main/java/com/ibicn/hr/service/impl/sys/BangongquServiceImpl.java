package com.ibicn.hr.service.impl.sys;

import com.ibicn.hr.entity.sys.Bangongqu;
import com.ibicn.hr.dao.sys.BangongquDao;
import com.ibicn.hr.service.sys.BangongquServiceI;
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
public class BangongquServiceImpl implements BangongquServiceI {
    @Autowired
    BangongquDao bangongquDao;

    @Override
    public Page list(Bangongqu data, BaseModel baseModel){
        Pageable pageable = PageRequest.of(baseModel.getPage() - 1, baseModel.getLimit());
        Specification<Bangongqu> specification = (Specification<Bangongqu>) (root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            // 第一个userId为CloudServerDao中的字段，第二个userId为参数
            if (StringUtil.isNotEmpty(data.getName())) {
                Predicate p1 = criteriaBuilder.like(root.get("name"), data.getName());
                list.add(p1);
            }
            return criteriaBuilder.and(list.toArray(new Predicate[0]));
        };
        Page<Bangongqu> all = bangongquDao.findAll(specification, pageable);
        return all;
    }

    @Override
    public Bangongqu getById(Integer id) {
        return bangongquDao.getOne(id);
    }

    @Override
    public void save(Bangongqu data) {
        bangongquDao.save(data);
    }

    @Override
    public void update(Bangongqu data) {
        bangongquDao.save(data);
    }

    @Override
    public void delete(Integer id) {
        bangongquDao.deleteById(id);
    }


    @Override
    public List<Bangongqu> getAllBangonqu() {
        List<Bangongqu> list=bangongquDao.findAll();
        if(CollectionUtil.size(list)>0){
            return list;
        }
        return Collections.emptyList();
    }
}
