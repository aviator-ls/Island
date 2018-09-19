package com.aviator.island.service.impl;

import com.aviator.island.dao.BaseDao;
import com.aviator.island.dao.ReplyPostDao;
import com.aviator.island.entity.po.ReplyPost;
import com.aviator.island.service.AbstractBaseService;
import com.aviator.island.service.ReplyPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 18057046 on 2018/8/16.
 */
@Service
public class ReplyPostServiceImpl extends AbstractBaseService<ReplyPost> implements ReplyPostService<ReplyPost>{
    @Autowired
    private ReplyPostDao<ReplyPost> replyPostDao;

    @Override
    protected BaseDao<ReplyPost> getBaseDao() {
        return this.replyPostDao;
    }
}
