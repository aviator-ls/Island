package com.aviator.island.dao.impl;

import com.aviator.island.dao.AbstractBaseDao;
import com.aviator.island.dao.BoardDao;
import com.aviator.island.entity.po.Board;
import org.springframework.stereotype.Repository;

/**
 * Created by 18057046 on 2018/7/17.
 */
@Repository
public class BoardDaoImpl extends AbstractBaseDao<Board> implements BoardDao<Board> {
}
