package com.aviator.island.service;

import com.aviator.island.entity.po.SearchPage;
import com.aviator.island.utils.Page;
import org.hibernate.criterion.Order;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by aviator_ls on 2018/8/3.
 */
public interface BaseService<T> {

    Serializable save(T entity);

    void remove(T entity);

    void remove(String id);

    void update(T entity);

    T get(Serializable id);

    boolean contains(T entity);

    List<T> findAll();

    List<T> findListByIds(Set<String> ids);

    Page<T> findPageListByCondition(SearchPage searchPage);

    Page<T> findPageListByCondition(SearchPage searchPage, Order order);

    /**
     * 根据关键词(版块名)分页查询版块列表
     *
     * @param searchPage
     * @param keywordParams
     * @return
     */
    Page<T> findPageListByKeyword(SearchPage searchPage, Map<String, Object> keywordParams);

}
