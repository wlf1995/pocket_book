package com.ibicn.hr.service.impl.person;

import com.ibicn.hr.dao.person.CompanyCVDao;
import com.ibicn.hr.entity.person.CompanyCV;
import com.ibicn.hr.service.base.BaseServiceImpl;
import com.ibicn.hr.service.person.CompanyCVServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CompanyCVServiceImpl extends BaseServiceImpl<CompanyCV> implements CompanyCVServiceI {

    @Autowired
    public CompanyCVServiceImpl(CompanyCVDao baseDao) {
        super(baseDao);
    }
}
