package com.ibicn.hr.service.sys;

import com.ibicn.hr.entity.sys.SystemConfig;
import com.ibicn.hr.service.base.BaseServiceI;

/**
 *
 */
public interface SystemConfigServiceI extends BaseServiceI<SystemConfig> {
    String getValue(String key);

    void put(String key, String value);

    void sendMail(String toAddress, String mailSubject, String mailBody);


    String getZongheUrl();

    String getZongheSystemId();

    String getZongheToken();
}
