package com.aviator.island.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by aviator_ls on 2018/7/16.
 */
@Slf4j
public abstract class AbstractBaseDao<T> implements BaseDao<T> {

    private Class<T> entityClass;

    @Autowired
    protected HibernateTemplate hibernateTemplate;

    public AbstractBaseDao() {
        Type type = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) type).getActualTypeArguments();
        this.entityClass = (Class) params[0];
    }

    @Override
    public Serializable save(T entity) {
        return this.hibernateTemplate.save(entity);
    }

    @Override
    public void remove(T entity) {
        this.hibernateTemplate.delete(entity);
    }

    @Override
    public void update(T entity) {
        this.hibernateTemplate.update(entity);
    }

    @Override
    public T get(Serializable id) {
        return this.hibernateTemplate.get(entityClass, id);
    }

    @Override
    public List<T> findAll() {
        return (List<T>) this.hibernateTemplate.findByCriteria(DetachedCriteria.forClass(entityClass));
    }

    @Override
    public List<T> findByCriteria(DetachedCriteria criteria) {
        return (List<T>) this.hibernateTemplate.findByCriteria(criteria);
    }

    @Override
    public List<T> findByCriteriaPageList(DetachedCriteria criteria, int firstResult, int pageSize) {
        return (List<T>) this.hibernateTemplate.findByCriteria(criteria, firstResult, pageSize);
    }

    @Override
    public long getDataCount(DetachedCriteria criteria) {
        Criteria c = criteria.getExecutableCriteria(getSession());
        c.setProjection(Projections.rowCount());
        Long totalNum = (Long) c.uniqueResult();
        return totalNum;
    }

    @Override
    public boolean contains(T entity) {
        return this.hibernateTemplate.contains(entity);
    }

    @Override
    public void deleteAll(Collection<T> entities) {
        this.hibernateTemplate.deleteAll(entities);
    }

    @Override
    public T load(Serializable id) {
        return this.hibernateTemplate.load(entityClass, id);
    }

    @Override
    public List<T> loadAll() {
        return this.hibernateTemplate.loadAll(entityClass);
    }

    /**
     * 对延迟加载的实体进行初始化
     *
     * @param entity
     */
    @Override
    public void initialize(Object entity) {
        this.hibernateTemplate.initialize(entity);
    }

    @Override
    public void evict(T entity) {
        this.hibernateTemplate.evict(entity);
    }

    @Override
    public void refresh(T entity) {
        this.hibernateTemplate.refresh(entity);
    }

    @Override
    public void clear() {
        this.hibernateTemplate.clear();
    }

    @Override
    public T merge(T entity) {
        return this.hibernateTemplate.merge(entity);
    }

    @Override
    public int executeUpdate(String hql, Map<String, Object> params) {
        return this.hibernateTemplate.execute(session -> {
            Query query = session.createQuery(hql);
            if (!CollectionUtils.isEmpty(params)) {
                params.forEach((k, v) -> query.setParameter(k, v));
            }
            return query.executeUpdate();
        });
    }

    @Override
    public T getSingleResult(String hql, Map<String, Object> params) {
        return this.hibernateTemplate.execute(session -> {
            Query query = session.createQuery(hql);
            if (!CollectionUtils.isEmpty(params)) {
                params.forEach((k, v) -> query.setParameter(k, v));
            }
            return (T) query.getSingleResult();
        });
    }

    @Override
    public int getDataCount(String hql, Map<String, Object> params) {
        return this.hibernateTemplate.execute(session -> {
            Query query = session.createQuery(hql);
            if (!CollectionUtils.isEmpty(params)) {
                params.forEach((k, v) -> query.setParameter(k, v));
            }
            Long count = (Long) query.getSingleResult();
            return count == null ? 0 : count.intValue();
        });
    }

    @Override
    public List<T> getResultList(String hql, Map<String, Object> params) {
        return getResultPageList(hql, -1, -1, params);
    }

    @Override
    public List<T> getResultPageList(String hql, int firstResult, int maxResults, Map<String, Object> params) {
        return this.hibernateTemplate.execute(session -> {
            Query query = session.createQuery(hql);
            if (firstResult > -1 && maxResults > -1) {
                query.setFirstResult(firstResult).setMaxResults(maxResults);
            }
            if (!CollectionUtils.isEmpty(params)) {
                params.forEach((k, v) -> query.setParameter(k, v));
            }
            return query.getResultList();
        });
    }

    protected Session getSession() {
        return this.hibernateTemplate.getSessionFactory().getCurrentSession();
    }

}
