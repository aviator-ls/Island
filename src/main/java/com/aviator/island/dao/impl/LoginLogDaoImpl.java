package com.aviator.island.dao.impl;

import com.aviator.island.dao.AbstractBaseDao;
import com.aviator.island.dao.LoginLogDao;
import com.aviator.island.entity.po.LoginLog;
import org.springframework.stereotype.Repository;

/**
 * Created by 18057046 on 2018/7/17.
 */
@Repository
public class LoginLogDaoImpl extends AbstractBaseDao<LoginLog> implements LoginLogDao<LoginLog> {
}
