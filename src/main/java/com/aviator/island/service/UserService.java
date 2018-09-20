package com.aviator.island.service;

import com.aviator.island.entity.po.Ask;
import com.aviator.island.entity.po.Post;
import com.aviator.island.entity.sys.Resource;
import com.aviator.island.entity.sys.Role;
import com.aviator.island.entity.sys.User;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created by aviator_ls on 2018/7/18.
 */
public interface UserService<T> extends BaseService<T> {
    User getUserByUserName(String userName);

    User getUserById(String id);

    Serializable register(User user);

    void lockedUser(String name);

    void unLockedUser(String name);

    Set<Role> findRoleSetByUserName(String name);

    Set<Resource> findResourceSetByUserName(String name);

    Set<String> findRoleStrSetByUserName(String name);

    Set<String> findResourceStrSetByUserName(String name);

    /**
     * 问答收藏
     *
     * @param user
     * @param askList
     */
    void collectAskList(User user, List<Ask> askList);

    /**
     * 文章收藏
     *
     * @param user
     * @param postList
     */
    void collectPostList(User user, List<Post> postList);
}
