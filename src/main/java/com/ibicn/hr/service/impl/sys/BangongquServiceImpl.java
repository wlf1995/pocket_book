package com.ibicn.hr.service.impl.sys;

import com.ibicn.hr.dao.sys.BangongquDao;
import com.ibicn.hr.entity.sys.OfficeArea;
import com.ibicn.hr.service.base.BaseServiceImpl;
import com.ibicn.hr.service.sys.BangongquServiceI;
import com.ibicn.hr.util.BaseModel;
import com.ibicn.hr.util.PageResult;
import com.ibicnCloud.util.CollectionUtil;
import com.ibicnCloud.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BangongquServiceImpl extends BaseServiceImpl<OfficeArea> implements BangongquServiceI {

    @Autowired
    public BangongquServiceImpl(BangongquDao baseDao) {
        super(baseDao);
    }

    @Override
    public PageResult list(OfficeArea data, BaseModel baseModel) {
        Pageable pageable = PageRequest.of(baseModel.getPage() - 1, baseModel.getLimit());
        Specification<OfficeArea> specification = (Specification<OfficeArea>) (root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            // 第一个userId为CloudServerDao中的字段，第二个userId为参数
            if (StringUtil.isNotEmpty(data.getAreaName())) {
                Predicate p1 = criteriaBuilder.like(root.get("areaName"), data.getAreaName());
                list.add(p1);
            }
            return criteriaBuilder.and(list.toArray(new Predicate[0]));
        };
        PageResult pageResult = super.pageList(specification, pageable);
        return pageResult;
    }

    @Override
    public List<OfficeArea> getAllBangonqu() {
        List<OfficeArea> list = super.all();
        if (CollectionUtil.size(list) > 0) {
            return list;
        }
        return Collections.emptyList();
    }
}
