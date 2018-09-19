package com.aviator.island.service.impl;

import com.aviator.island.dao.BaseDao;
import com.aviator.island.dao.TagDao;
import com.aviator.island.entity.po.Tag;
import com.aviator.island.service.AbstractBaseService;
import com.aviator.island.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by aviator_ls on 2018/8/15.
 */
@Service
public class TagServiceImpl extends AbstractBaseService<Tag> implements TagService<Tag> {

    @Autowired
    private TagDao<Tag> tagDao;

    @Override
    protected BaseDao<Tag> getBaseDao() {
        return this.tagDao;
    }

}
