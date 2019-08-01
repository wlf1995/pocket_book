/*
package com.ibicn.hr.service.base;

import com.ibicn.hr.dao.base.BaseDaoI;
import com.ibicn.hr.util.BaseModel;
import com.ibicn.hr.util.Pageutil;
import com.ibicnCloud.util.PagedResult;
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

*/
/**
 * 基础业务逻辑类，其他service继承此service获得基本的业务
 *
 * @param <T>
 * @author 王立方
 *//*

public class BaseServiceJpaImpl<T,ID> implements BaseServiceJpaI<T> {
    @Autowired
    private EntityManager entityManager;

    private BaseDaoI<T,ID> baseDao;

    public BaseServiceJpaImpl(BaseDaoI<T,ID> baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public PagedResult list(BaseModel baseModel) {
        Page<T> all = baseDao.findAll(getPageable(baseModel));
        return Pageutil.getPageResult(all);
    }

    @Override
    public PagedResult list(T data, BaseModel baseModel) {
        return this.list(baseModel);
    }

    @Override
    public PagedResult list(Specification<T> specification, BaseModel baseModel) {
        PagedResult list = this.list(specification, getPageable(baseModel));
        return list;
    }

    @Override
    public PagedResult list(Specification<T> specification, Pageable pageable) {
        Page<T> all = baseDao.findAll(specification, pageable);
        PagedResult pageResult = Pageutil.getPageResult(all);
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
        baseDao.deleteById((ID) id);
    }

    @Override
    public T getOne(Integer dataId) {
        T one = baseDao.getOne((ID) dataId);
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
*/
