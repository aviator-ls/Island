package com.aviator.island.service.impl;

import com.aviator.island.dao.AnswerDao;
import com.aviator.island.dao.BaseDao;
import com.aviator.island.entity.po.Answer;
import com.aviator.island.service.AbstractBaseService;
import com.aviator.island.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 18057046 on 2018/8/26.
 */
@Service
public class AnswerServiceImpl extends AbstractBaseService<Answer> implements AnswerService<Answer> {
    @Autowired
    private AnswerDao<Answer> answerDao;

    @Override
    protected BaseDao<Answer> getBaseDao() {
        return this.answerDao;
    }
}
