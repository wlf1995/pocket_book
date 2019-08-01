package com.ibicn.hr.service.impl.sys;

import com.ibicn.hr.entity.sys.SystemConfig;
import com.ibicn.hr.dao.sys.SystemConfigDao;
import com.ibicn.hr.service.sys.SystemConfigServiceI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * 
 * @author abel
 *
 */
@Slf4j
@Service
@Transactional
public class SystemConfigServiceImpl  implements SystemConfigServiceI {
    @Autowired
    SystemConfigDao configDao;

    @Override
    public String getValue(String key) {
        SystemConfig config=this.getConfig(key);
        if(config!=null){
            return config.getMValue();
        }
       return "";
    }

    private SystemConfig getConfig(String key) {
        Specification<SystemConfig> specification = (Specification<SystemConfig>) (root, query, criteriaBuilder) -> {
            // 第一个userId为CloudServerDao中的字段，第二个userId为参数
            Predicate p1 = criteriaBuilder.equal(root.get("mKey"), key);
            return criteriaBuilder.and(p1);
        };
        List<SystemConfig> result = configDao.findAll(specification);
        if (result != null && result.size() == 1) {
            return result.get(0);
        } else {
            return null;
        }
    }


    @Override
    public void put(String key, String value) {
        SystemConfig config=this.getConfig(key);
        if(config==null){
            config=new SystemConfig();
            config.setMKey(key);
            config.setMValue(value);
        }else{
            config.setMValue(value);
        }
        configDao.save(config);
    }


    @Override
    public void sendMail(String toAddress, String mailSubject, String mailBody) {
    }
    @Override
    public String getZongheUrl() {
        return this.getValue("ZongheUrl");
    }

    @Override
    public String getZongheSystemId() {
        return this.getValue("heSystemId");
    }

    @Override
    public String getZongheToken() {
        return this.getValue("ZongheToken");
    }

}
