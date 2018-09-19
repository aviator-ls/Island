package com.aviator.island.dao.impl;

import com.aviator.island.dao.AbstractBaseDao;
import com.aviator.island.dao.TagDao;
import com.aviator.island.entity.po.Tag;
import org.springframework.stereotype.Repository;

/**
 * Created by aviator_ls on 2018/8/15.
 */
@Repository
public class TagDaoImpl extends AbstractBaseDao<Tag> implements TagDao<Tag> {
}
