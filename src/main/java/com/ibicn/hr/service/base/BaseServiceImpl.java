package com.ibicn.hr.service.base;

import com.ibicn.hr.dao.base.BaseDaoI;
import com.ibicn.hr.util.BaseModel;
import com.ibicn.hr.util.PageResult;
import com.ibicnCloud.util.StringUtil;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

/**
 * 基础业务逻辑类，其他service继承此service获得基本的业务
 *
 * @param <T>
 * @author 王立方
 */

public class BaseServiceImpl<T> implements BaseServiceI<T> {
    @Autowired
    private EntityManager entityManager;

    private BaseDaoI<T> baseDao;

    public BaseServiceImpl(BaseDaoI<T> baseDao) {
        this.baseDao = baseDao;
    }

    //查询列表的方法,实际使用时需重写此方法
    @Override
    public PageResult list(T data, BaseModel baseModel) {
        PageResult pageResult = this.pageList(data, baseModel);
        return pageResult;
    }

    @Override
    public PageResult pageList(BaseModel baseModel) {
        Page<T> all = baseDao.findAll(getPageable(baseModel));
        return PageResult.getPageResult(all);
    }

    @Override
    public PageResult pageList(T data, BaseModel baseModel) {
        return this.pageList(baseModel);
    }

    @Override
    public PageResult pageList(Specification<T> specification, BaseModel baseModel) {
        PageResult list = this.pageList(specification, getPageable(baseModel));
        return list;
    }

    @Override
    public PageResult pageList(Specification<T> specification, Pageable pageable) {
        Page<T> all = baseDao.findAll(specification, pageable);
        PageResult pageResult = PageResult.getPageResult(all);
        return pageResult;
    }

    @Override
    public List<T> all() {
        List<T> all = baseDao.findAll();
        return all;
    }

    @Override
    public List<T> all(Specification specification) {
        List<T> all = baseDao.findAll(specification);
        return all;
    }

    @Override
    public T save(T data) {
        data = baseDao.save(data);
        return data;
    }

    @Override
    public void delete(T data) {
        baseDao.delete(data);
    }

    @Override
    public void deleteById(Integer id) {
        baseDao.deleteById(id);
    }

    @Override
    public T getOne(Integer dataId) {
        T one = baseDao.getOne(dataId);
        return one;
    }

    @Override
    public T update(T data) {
        data = baseDao.save(data);
        return data;
    }

    @Override
    public List<Map> getBySql(String sql) {
        Query nativeQuery = entityManager.createNativeQuery(sql);
        nativeQuery.unwrap(NativeQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map> all = nativeQuery.getResultList();
        return all;
    }

    @Override
    public List<Map> getBySql(String sql, Object... parameters) {
        Query nativeQuery = entityManager.createNativeQuery(sql);
        nativeQuery.unwrap(NativeQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        setParameter(nativeQuery, parameters);
        List<Map> all = nativeQuery.getResultList();
        return all;
    }

    @Override
    public List<Map> getBySql(String sql, HashMap<String, Object> parameters) {
        Query nativeQuery = entityManager.createNativeQuery(sql);
        nativeQuery.unwrap(NativeQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        setParameter(nativeQuery, parameters);
        List<Map> all = nativeQuery.getResultList();
        return all;
    }


    @Override
    public List getBySqlProject(String sql, Class c) {
        Query nativeQuery = entityManager.createNativeQuery(sql, c);
        List all = nativeQuery.getResultList();
        return all;
    }

    @Override
    public List getBySqlProject(String sql, Class c, Object... parameters) {
        Query nativeQuery = entityManager.createNativeQuery(sql, c);
        setParameter(nativeQuery, parameters);
        List all = nativeQuery.getResultList();
        return all;
    }

    @Override
    public List getBySqlProject(String sql, Class c, HashMap<String, Object> parameters) {
        Query nativeQuery = entityManager.createNativeQuery(sql, c);
        setParameter(nativeQuery, parameters);
        List all = nativeQuery.getResultList();
        return all;
    }

    @Override
    public List<Map> getByHql(String hql) {
        Query nativeQuery = entityManager.createQuery(hql);
        nativeQuery.unwrap(NativeQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List all = nativeQuery.getResultList();
        return all;
    }

    @Override
    public List<Map> getByHql(String hql, Object... parameters) {
        Query nativeQuery = entityManager.createQuery(hql);
        nativeQuery.unwrap(NativeQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        setParameter(nativeQuery, parameters);
        List all = nativeQuery.getResultList();
        return all;
    }

    @Override
    public List<Map> getByHql(String hql, HashMap<String, Object> parameters) {
        Query query = entityManager.createQuery(hql);
        query.unwrap(NativeQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        setParameter(query, parameters);
        List all = query.getResultList();
        return all;
    }

    @Override
    public List getByHqlProject(String hql, Class c) {
        Query nativeQuery = entityManager.createQuery(hql, c);
        List all = nativeQuery.getResultList();
        return all;
    }

    @Override
    public List getByHqlProject(String hql, Class c, Object... parameters) {
        Query nativeQuery = entityManager.createQuery(hql, c);
        setParameter(nativeQuery, parameters);
        List all = nativeQuery.getResultList();
        return all;
    }

    @Override
    public List getByHqlProject(String hql, Class c, HashMap<String, Object> parameters) {
        Query nativeQuery = entityManager.createQuery(hql, c);
        setParameter(nativeQuery, parameters);
        List all = nativeQuery.getResultList();
        return all;
    }

    //把baseModel 转换为分页对象
    private Pageable getPageable(BaseModel baseModel) {
        Sort by = null;
        if (StringUtil.isNotEmpty(baseModel.getSort())) {
            if ("desc".equals(baseModel.getOrder())) {
                by = Sort.by(Sort.Order.desc(baseModel.getSort()));
            } else {
                by = Sort.by(Sort.Order.asc(baseModel.getSort()));
            }
        }
        if (by == null) {
            return PageRequest.of(baseModel.getPage() - 1, baseModel.getLimit());
        }
        return PageRequest.of(baseModel.getPage() - 1, baseModel.getLimit(), by);
    }


    private void setParameter(Query nativeQuery, Object[] parameters) {
        for (int i = 0; i < parameters.length; i++) {
            nativeQuery.setParameter(i, parameters[i]);
        }
    }

    private void setParameter(Query nativeQuery, Map<String, Object> parameters) {
        if (parameters != null) {
            Set<String> keySet = parameters.keySet();
            Iterator<String> iterator = keySet.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                nativeQuery.setParameter(key, parameters.get(key));
            }
        }
    }
}
