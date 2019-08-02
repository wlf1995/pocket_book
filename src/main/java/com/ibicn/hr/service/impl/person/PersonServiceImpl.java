package com.ibicn.hr.service.impl.person;

import com.ibicn.hr.dao.person.PersonDao;
import com.ibicn.hr.entity.person.Person;
import com.ibicn.hr.service.base.BaseServiceImpl;
import com.ibicn.hr.service.person.PersonServiceI;
import com.ibicn.hr.util.BaseModel;
import com.ibicn.hr.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class PersonServiceImpl extends BaseServiceImpl<Person> implements PersonServiceI {

    @Autowired
    public PersonServiceImpl(PersonDao baseDao) {
        super(baseDao);
    }

    @Override
    public PageResult list(Person data, BaseModel baseModel) {
        Pageable pageable = PageRequest.of(baseModel.getPage() - 1, baseModel.getLimit());
        Specification<Person> specification = (Specification<Person>) (root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            // 第一个userId为CloudServerDao中的字段，第二个userId为参数
            if (data.getSex()!=null) {
                Predicate p1 = criteriaBuilder.equal(root.get("sex"), data.getSex().getIndex());
                list.add(p1);
            }
            return criteriaBuilder.and(list.toArray(new Predicate[0]));
        };
        PageResult pageResult = super.pageList(specification, pageable);
        return pageResult;
    }

}
