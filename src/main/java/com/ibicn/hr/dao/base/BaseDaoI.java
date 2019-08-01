package com.ibicn.hr.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * jpa基础接口,其它dao层接口继承此接口
 */
@NoRepositoryBean
public interface BaseDaoI<T> extends JpaRepository<T, Integer>, JpaSpecificationExecutor<T> {

}
