package com.ibicn.hr.util;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * 存放来自页面的参数
 *
 * @author yanghao
 */
@Getter
@Setter
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


    public void setPage(int page) {
        this.page = page;
    }


    public void setLimit(int limit) {
        this.limit = limit;
    }


    public BaseModel setSort(String sort) {
        this.sort = sort;
        return this;
    }


    public BaseModel setOrder(String order) {
        this.order = order;
        return this;
    }

    public BaseModel setQ(String q) {
        this.q = q;
        return this;
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

}
