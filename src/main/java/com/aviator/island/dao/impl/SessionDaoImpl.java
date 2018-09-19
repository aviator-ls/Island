package com.aviator.island.dao.impl;

import com.aviator.island.dao.AbstractBaseDao;
import com.aviator.island.dao.SessionDao;
import com.aviator.island.entity.sys.MySession;
import org.springframework.stereotype.Repository;

/**
 * Created by 18057046 on 2018/8/2.
 */
@Repository
public class SessionDaoImpl extends AbstractBaseDao<MySession> implements SessionDao<MySession>{
}
