package com.aviator.island.dao;

import org.hibernate.criterion.DetachedCriteria;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by aviator_ls on 2018/7/18.
 */
public interface BaseDao<T> {
    T load(Serializable id);

    List<T> loadAll();

    Serializable save(T entity);

    void remove(T entity);

    void update(T entity);

    T get(Serializable id);

    List<T> findAll();

    List<T> findByCriteria(DetachedCriteria criteria);

    List<T> findByCriteriaPageList(DetachedCriteria criteria, int firstResult, int pageSize);

    long getDataCount(DetachedCriteria criteria);

    boolean contains(T entity);

    void deleteAll(Collection<T> entities);

    void evict(T entity);

    void refresh(T entity);

    void clear();

    T merge(T entity);

    void initialize(T entity);

    int executeUpdate(String hql, Map<String, Object> params);

    int getDataCount(String hql, Map<String, Object> params);

    T getSingleResult(String hql, Map<String, Object> params);

    List<T> getResultList(String hql, Map<String, Object> params);

    List<T> getResultPageList(String hql, int firstResult, int maxResults, Map<String, Object> params);
}
