package com.aviator.island.service;

import com.aviator.island.entity.po.PostSpecial;
import com.aviator.island.entity.sys.User;

import java.util.List;

/**
 * Created by 18057046 on 2018/9/12.
 */
public interface PostSpecialService<T> extends BaseService<T> {
    List<PostSpecial> findListByUser(User user);
}
