package com.aviator.island.service.impl;

import com.aviator.island.constants.Constants;
import com.aviator.island.dao.BaseDao;
import com.aviator.island.dao.PagePicDao;
import com.aviator.island.entity.po.PagePic;
import com.aviator.island.service.AbstractBaseService;
import com.aviator.island.service.PagePicService;
import com.aviator.island.utils.ConfigUtil;
import com.aviator.island.utils.CriteriaBuilder;
import com.aviator.island.utils.SearchConditional;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagePicServiceImpl extends AbstractBaseService<PagePic> implements PagePicService<PagePic> {

    @Autowired
    private PagePicDao pagePicDao;

    public List<PagePic> findIndexCarouselPicList() {
        DetachedCriteria criteria = CriteriaBuilder.newDetachedCriteriaBuilder(this.entityClass).andConditional("type", 1, SearchConditional.EQ).build();
        int pageSize = ConfigUtil.getConfigValue(Constants.CONFIG.INDEX_CAROUSEL_PIC_PAGE_SIZE, Constants.CONFIG_DEFAULT.INDEX_CAROUSEL_PIC_PAGE_SIZE);
        return this.pagePicDao.findByCriteriaPageList(criteria, 1, pageSize);
    }

    @Override
    protected BaseDao getBaseDao() {
        return this.pagePicDao;
    }
}
