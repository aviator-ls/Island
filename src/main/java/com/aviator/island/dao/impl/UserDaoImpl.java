package com.aviator.island.dao.impl;

import com.aviator.island.dao.AbstractBaseDao;
import com.aviator.island.dao.UserDao;
import com.aviator.island.entity.sys.User;
import org.springframework.stereotype.Repository;

/**
 * Created by aviator_ls on 2018/7/17.
 */
@Repository
public class UserDaoImpl extends AbstractBaseDao<User> implements UserDao<User> {

}
