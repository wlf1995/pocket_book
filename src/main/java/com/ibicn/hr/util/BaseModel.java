package com.ibicn.hr.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.Map;

/**
 * 存放来自页面的参数
 *
 * @author yanghao
 */
public class BaseModel {
    protected int page = 1;// 当前页
    protected int limit = 10;// 每页显示记录数
    protected String sort;// 排序字段
    protected String order = "desc";// asc/desc
    protected String q;// easyui的combo和其子类过滤时使用

    protected String ids;// 主键集合，逗号分割

    private String fields; //需要调取的字段信息，用逗号分隔

    private Map<String, Object> parameterMap = new HashMap<>();

    private boolean ismobile = false;//是否是移动端

    public String getFields() {
        return fields;
    }

    public BaseModel setFields(String fields) {
        this.fields = fields;
        return this;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getSort() {
        return sort;
    }

    public BaseModel setSort(String sort) {
        this.sort = sort;
        return this;
    }

    public String getOrder() {
        return order;
    }

    public BaseModel setOrder(String order) {
        this.order = order;
        return this;
    }

    public String getQ() {
        return q;
    }

    public BaseModel setQ(String q) {
        this.q = q;
        return this;
    }

    public String getIds() {
        return ids;
    }

    public BaseModel setIds(String ids) {
        this.ids = ids;
        return this;
    }

    public BaseModel() {
    }

    public BaseModel(int limit, int page) {
        this.limit = limit;
        this.page = page;
    }

    public Pageable getJPAPage(){
        PageRequest of = PageRequest.of(this.page, this.limit);
        return of;
    }
    public Pageable getJPAPageAndSort(){
        PageRequest of = PageRequest.of(this.page, this.limit);
        return of;
    }
    public Map<String, Object> getParameterMap() {
        return parameterMap;
    }

    public void setParameterMap(Map<String, Object> parameterMap) {
        this.parameterMap = parameterMap;
    }

    public boolean getIsmobile() {
        return ismobile;
    }

    public void setIsmobile(boolean ismobile) {
        this.ismobile = ismobile;
    }
}
