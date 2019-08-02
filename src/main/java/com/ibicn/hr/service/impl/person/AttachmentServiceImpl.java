package com.ibicn.hr.service.impl.person;

import com.ibicn.hr.dao.person.AttachmentDao;
import com.ibicn.hr.entity.person.Attachment;
import com.ibicn.hr.service.base.BaseServiceImpl;
import com.ibicn.hr.service.person.AttachmentServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class AttachmentServiceImpl extends BaseServiceImpl<Attachment> implements AttachmentServiceI {

    @Autowired
    public AttachmentServiceImpl(AttachmentDao baseDao) {
        super(baseDao);
    }
}
