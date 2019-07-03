package com.ibicn.hr.service.sys;
/**
 */
public interface SystemConfigServiceI   {
    String getValue(String key);
    void put(String key, String value);
    void sendMail(String toAddress, String mailSubject, String mailBody);



    String getZongheUrl();
    String getZongheSystemId();
    String getZongheToken();
}
