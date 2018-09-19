package com.aviator.island.service.impl;

import com.aviator.island.dao.BaseDao;
import com.aviator.island.dao.LoginLogDao;
import com.aviator.island.entity.po.LoginLog;
import com.aviator.island.service.AbstractBaseService;
import com.aviator.island.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 18057046 on 2018/7/18.
 */
@Service
public class LoginLogServiceImpl extends AbstractBaseService<LoginLog> implements LoginLogService<LoginLog> {
    @Autowired
    private LoginLogDao<LoginLog> loginLogDao;

    public void recordUserLogin(LoginLog loginLog) {
        loginLogDao.save(loginLog);
    }

    @Override
    protected BaseDao getBaseDao() {
        return this.loginLogDao;
    }

}
