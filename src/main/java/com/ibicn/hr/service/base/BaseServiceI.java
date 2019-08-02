package com.ibicn.hr.service.base;

import com.ibicn.hr.util.BaseModel;
import com.ibicn.hr.util.PageResult;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基础业务逻辑类，其他service继承此service获得基本的业务
 *
 * @param <T>
 * @author 王立方
 */
public interface BaseServiceI<T> {
    PageResult list(T data, BaseModel baseModel) ;
    PageResult pageList(BaseModel baseModel);

    PageResult pageList(T data, BaseModel baseModel);

    PageResult pageList(Specification<T> specification, BaseModel baseModel);

    PageResult pageList(Specification<T> specification, Pageable pageable);

    List<T> all();

    List<T> all(Specification specification);

    List<Map> getBySql(String sql);

    List<Map> getBySql(String sql, Object... parameters);

    List<Map> getBySql(String sql, HashMap<String, Object> parameters);

    List getBySqlProject(String sql, Class c);

    List getBySqlProject(String sql, Class c, Object... parameters);

    List getBySqlProject(String sql, Class c, HashMap<String, Object> parameters);

    List<Map> getByHql(String hql);

    List<Map> getByHql(String hql, Object... parameters);

    List<Map> getByHql(String hql, HashMap<String, Object> parameters);

    List getByHqlProject(String hql, Class c);

    List getByHqlProject(String hql, Class c, Object... parameters);

    List getByHqlProject(String hql, Class c, HashMap<String, Object> parameters);


    T save(T data);

    void delete(T data);

    void delete(Integer id);

    void deleteById(Integer id);

    T getOne(Integer id);

    T update(T data);
}
