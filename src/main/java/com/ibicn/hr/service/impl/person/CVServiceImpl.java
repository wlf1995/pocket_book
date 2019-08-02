package com.ibicn.hr.service.impl.person;

import com.ibicn.hr.dao.person.CVDao;
import com.ibicn.hr.entity.person.CV;
import com.ibicn.hr.service.base.BaseServiceImpl;
import com.ibicn.hr.service.person.CVServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CVServiceImpl extends BaseServiceImpl<CV> implements CVServiceI {

    @Autowired
    public CVServiceImpl(CVDao baseDao) {
        super(baseDao);
    }
}
