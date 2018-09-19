package com.aviator.island.service;

import com.aviator.island.dao.BaseDao;
import com.aviator.island.entity.po.SearchPage;
import com.aviator.island.exception.NullBaseDaoException;
import com.aviator.island.utils.CriteriaBuilder;
import com.aviator.island.utils.OrderUtil;
import com.aviator.island.utils.Page;
import com.aviator.island.utils.SearchConditional;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by 18057046 on 2018/7/20.
 */
@Transactional
public abstract class AbstractBaseService<T> implements BaseService<T>, InitializingBean {
    protected static final Logger logger = LoggerFactory.getLogger(AbstractBaseService.class);

    protected Class<T> entityClass;

    private BaseDao<T> baseDao;

    public AbstractBaseService() {
        Type type = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) type).getActualTypeArguments();
        this.entityClass = (Class) params[0];
    }

    @Override
    public Serializable save(T entity) {
        return baseDao.save(entity);
    }

    @Override
    public void remove(T entity) {
        baseDao.remove(entity);
    }

    @Override
    public void remove(String id) {
        try {
            T instance = get(id);
            if (instance != null) {
                baseDao.remove(instance);
            }
        } catch (Exception e) {
            logger.error("remove error, id:{}", id, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(T entity) {
        baseDao.update(entity);
    }

    @Override
    public T get(Serializable id) {
        return baseDao.get(id);
    }

    @Override
    public boolean contains(T entity) {
        return baseDao.contains(entity);
    }

    @Override
    public List<T> findAll() {
        return baseDao.findAll();
    }

    public List<T> findListByIds(Set<String> ids) {
        DetachedCriteria criteria = CriteriaBuilder.newDetachedCriteriaBuilder(entityClass).andConditional("id", ids, SearchConditional.IN).build();
        return baseDao.findByCriteria(criteria);
    }

    @Override
    public Page<T> findPageListByCondition(SearchPage searchPage) {
        return findPageListByCondition(searchPage, null);
    }

    @Override
    public Page<T> findPageListByCondition(SearchPage searchPage, Order order) {
        CriteriaBuilder.DetachedCriteriaBuilder builder = CriteriaBuilder.newDetachedCriteriaBuilder(entityClass);
        Map<String, Object> searchAndParams = searchPage.getSearchAndParams();
        Map<String, Object> searchOrParams = searchPage.getSearchOrParams();
        if (!CollectionUtils.isEmpty(searchAndParams)) {
            builder.andConditional(searchAndParams, SearchConditional.EQ);
        }
        if (!CollectionUtils.isEmpty(searchOrParams)) {
            builder.orConditional(searchOrParams, SearchConditional.EQ);
        }
        return findPageListByCondition(searchPage, builder, order);
    }

    protected Page<T> findPageListByCondition(SearchPage searchPage, CriteriaBuilder.DetachedCriteriaBuilder builder, Order order) {
        int pageNum = searchPage.getPageNum();
        int pageSize = searchPage.getPageSize();
        if (order == null) {
            if (StringUtils.isNotBlank(searchPage.getOrderBy())) {
                order = OrderUtil.order(searchPage.getOrderBy(), searchPage.isAsc());
            }
        }
        DetachedCriteria countCriteria = builder.build();
        DetachedCriteria listCriteria = builder.build();
        long dataCount = baseDao.getDataCount(countCriteria);
        if (dataCount < 1) {
            return new Page();
        }
        if (pageNum < 1) {
            pageNum = Page.DEFAULT_PAGE_NUM;
        }
        if (pageSize < 1) {
            pageSize = Page.DEFAULT_PAGE_SIZE;
        }
        int firstResult = (pageNum - 1) * pageSize;
        if (order != null) {
            listCriteria.addOrder(order);
        }
        List<T> result = this.baseDao.findByCriteriaPageList(listCriteria, firstResult, pageSize);
        if (order == null) {
            return new Page(pageNum, pageSize, result, dataCount);
        }
        return new Page(pageNum, pageSize, result, dataCount, order.getPropertyName(), order.isAscending());
    }

    protected List<T> findListByCondition(DetachedCriteria criteria) {
        return this.baseDao.findByCriteria(criteria);
    }

    @Override
    public Page<T> findPageListByKeyword(SearchPage searchPage, Map<String, Object> keywordParams) {
        if (!CollectionUtils.isEmpty(keywordParams)) {
            CriteriaBuilder.DetachedCriteriaBuilder builder = CriteriaBuilder.newDetachedCriteriaBuilder(this.entityClass);
            keywordParams.forEach((k, v) -> builder.orConditional(k, v, SearchConditional.LIKE));
            return this.findPageListByCondition(searchPage, builder, null);
        }
        return this.findPageListByCondition(searchPage);
    }

    protected abstract BaseDao<T> getBaseDao();

    public void afterPropertiesSet() throws Exception {
        this.baseDao = getBaseDao();
        if (this.baseDao == null) {
            throw new NullBaseDaoException("baseDao is null");
        }
    }
}
