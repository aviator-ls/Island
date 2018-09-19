package com.aviator.island.service.impl;

import com.google.common.collect.Maps;
import com.aviator.island.dao.BaseDao;
import com.aviator.island.dao.BoardDao;
import com.aviator.island.entity.po.Board;
import com.aviator.island.entity.po.SearchPage;
import com.aviator.island.service.AbstractBaseService;
import com.aviator.island.service.BoardService;
import com.aviator.island.utils.CriteriaBuilder;
import com.aviator.island.utils.Page;
import com.aviator.island.utils.SearchConditional;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by 18057046 on 2018/7/18.
 */
@Service
public class BoardServiceImpl extends AbstractBaseService<Board> implements BoardService<Board> {
    @Autowired
    private BoardDao<Board> boardDao;

    @Override
    protected BaseDao getBaseDao() {
        return this.boardDao;
    }

    @Override
    public List<Board> findListByLevel(int level) {
        DetachedCriteria criteria = CriteriaBuilder.newDetachedCriteriaBuilder(this.entityClass).andConditional("level", level, SearchConditional.EQ).build();
        return this.findListByCondition(criteria);
    }

    @Override
    public List<Board> findListByParentBoardId(String parentBoardId) {
        Board parentBoard = boardDao.get(parentBoardId);
        DetachedCriteria criteria = CriteriaBuilder.newDetachedCriteriaBuilder(this.entityClass).andConditional("parentBoard", parentBoard, SearchConditional.EQ).build();
        return this.findListByCondition(criteria);
    }

    @Override
    public List<Board> findLeafList() {
        DetachedCriteria criteria = CriteriaBuilder.newDetachedCriteriaBuilder(this.entityClass).andConditional("childBoardSet", null, SearchConditional.IS_EMPTY).build();
        return this.findListByCondition(criteria);
    }

    @Override
    public List<Board> findParentBoardList() {
        DetachedCriteria criteria = CriteriaBuilder.newDetachedCriteriaBuilder(this.entityClass).andConditional("level", 3, SearchConditional.LT).build();
        return this.findListByCondition(criteria);
    }

    @Override
    public Page<Board> findPageListByKeyword(String keyword, SearchPage searchPage) {
        Map<String, Object> params = null;
        if (StringUtils.isNotBlank(keyword)) {
            params = Maps.newHashMap();
            params.put("name", keyword);
        }
        return this.findPageListByKeyword(searchPage, params);
    }

}
