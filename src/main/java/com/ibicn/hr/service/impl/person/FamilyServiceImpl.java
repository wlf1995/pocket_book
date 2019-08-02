package com.ibicn.hr.service.impl.person;

import com.ibicn.hr.dao.person.FamilyDao;
import com.ibicn.hr.entity.person.Family;
import com.ibicn.hr.service.base.BaseServiceImpl;
import com.ibicn.hr.service.person.FamilyServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class FamilyServiceImpl extends BaseServiceImpl<Family> implements FamilyServiceI {

    @Autowired
    public FamilyServiceImpl(FamilyDao baseDao) {
        super(baseDao);
    }
}
