package com.aviator.island.service.impl;

import com.google.common.collect.Maps;
import com.aviator.island.constants.Constants;
import com.aviator.island.dao.BaseDao;
import com.aviator.island.dao.PostDao;
import com.aviator.island.entity.po.Post;
import com.aviator.island.entity.po.SearchPage;
import com.aviator.island.service.AbstractBaseService;
import com.aviator.island.service.PostService;
import com.aviator.island.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by aviator_ls on 2018/7/18.
 */
@Service
public class PostServiceImpl extends AbstractBaseService<Post> implements PostService<Post> {
    @Autowired
    private PostDao<Post> postDao;

    @Override
    public Post view(String id) {
        Post post = postDao.get(id);
        // 浏览+1
        post.setViews(post.getViews() + 1);
        return post;
    }

    @Override
    public Page<Post> findPageListNew(SearchPage searchPage) {
        Order order = OrderUtil.order("createTime", false);
        return this.findPageListByCondition(searchPage, order);
    }

    @Override
    public Page<Post> findPageListHot(SearchPage searchPage) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("views", ConfigUtil.getConfigValue(Constants.CONFIG.HOT_POST_VIEW, Constants.CONFIG_DEFAULT.HOT_POST_VIEW));
        CriteriaBuilder.DetachedCriteriaBuilder builder = CriteriaBuilder.newDetachedCriteriaBuilder(this.entityClass).andConditional(params, SearchConditional.GE);
        return this.findPageListByCondition(searchPage, builder, null);
    }

    @Override
    public Page<Post> findPageListBoutique(SearchPage searchPage) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("boutique", 1);
        searchPage.setSearchAndParams(params);
        return this.findPageListByCondition(searchPage);
    }

    @Override
    public Page<Post> findPageListByKeyword(String keyword, SearchPage searchPage) {
        Map<String, Object> params = null;
        if (StringUtils.isNotBlank(keyword)) {
            params = Maps.newHashMap();
            params.put("title", keyword);
        }
        return this.findPageListByKeyword(searchPage, params);
    }

    @Override
    protected BaseDao getBaseDao() {
        return this.postDao;
    }
}
