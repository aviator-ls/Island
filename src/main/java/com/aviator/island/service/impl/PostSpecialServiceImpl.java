package com.aviator.island.service.impl;

import com.aviator.island.dao.BaseDao;
import com.aviator.island.dao.PostSpecialDao;
import com.aviator.island.entity.po.PostSpecial;
import com.aviator.island.entity.sys.User;
import com.aviator.island.service.AbstractBaseService;
import com.aviator.island.service.PostSpecialService;
import com.aviator.island.utils.CriteriaBuilder;
import com.aviator.island.utils.SearchConditional;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by aviator_ls on 2018/9/12.
 */
@Service
public class PostSpecialServiceImpl extends AbstractBaseService<PostSpecial> implements PostSpecialService<PostSpecial> {
    @Autowired
    private PostSpecialDao<PostSpecial> postSpecialDao;

    public List<PostSpecial> findListByUser(User user) {
        DetachedCriteria criteria = CriteriaBuilder.newDetachedCriteriaBuilder(this.entityClass).andConditional("user", user, SearchConditional.EQ).build();
        return postSpecialDao.findByCriteria(criteria);
    }

    @Override
    protected BaseDao<PostSpecial> getBaseDao() {
        return this.postSpecialDao;
    }
}
