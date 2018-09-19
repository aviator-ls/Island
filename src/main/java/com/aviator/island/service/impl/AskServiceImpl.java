package com.aviator.island.service.impl;

import com.google.common.collect.Maps;
import com.aviator.island.constants.Constants;
import com.aviator.island.dao.AskDao;
import com.aviator.island.dao.BaseDao;
import com.aviator.island.entity.po.Ask;
import com.aviator.island.entity.po.SearchPage;
import com.aviator.island.service.AbstractBaseService;
import com.aviator.island.service.AskService;
import com.aviator.island.utils.*;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by aviator_ls on 2018/8/26.
 */
@Service
public class AskServiceImpl extends AbstractBaseService<Ask> implements AskService<Ask> {

    @Autowired
    private AskDao<Ask> askDao;

    @Override
    public Page<Ask> findPageListNew(SearchPage searchPage) {
        Order order = OrderUtil.order("createTime", false);
        return this.findPageListByCondition(searchPage, order);
    }

    @Override
    public Page<Ask> findPageListHot(SearchPage searchPage) {
        int pageNum = searchPage.getPageNum();
        int pageSize = searchPage.getPageSize();
        Order order = OrderUtil.order("createTime", false);
        String countHql = "select count(*) from Ask as a where size(a.answerSet) > :size";
        Map<String, Object> params = Maps.newHashMap();
        params.put("size", ConfigUtil.getConfigValue(Constants.CONFIG.HOT_ASK_ANSWER, Constants.CONFIG_DEFAULT.HOT_ASK_ANSWER));
        int dataCount = askDao.getDataCount(countHql, params);
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
        String dataHql = "select * from Ask as a where size(a.answerSet) > :size";
        List<Ask> result = askDao.getResultPageList(dataHql, firstResult, pageSize, params);
        return new Page(pageNum, pageSize, result, dataCount, order.getPropertyName(), order.isAscending());
    }

    @Override
    public Page<Ask> findPageListResolved(SearchPage searchPage) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("type", 2);
        searchPage.setSearchAndParams(params);
        return this.findPageListByCondition(searchPage);
    }

    @Override
    protected BaseDao<Ask> getBaseDao() {
        return this.askDao;
    }
}
