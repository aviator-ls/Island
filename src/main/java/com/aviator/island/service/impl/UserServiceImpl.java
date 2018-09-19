package com.aviator.island.service.impl;

import com.aviator.island.dao.BaseDao;
import com.aviator.island.dao.UserDao;
import com.aviator.island.entity.sys.Resource;
import com.aviator.island.entity.sys.Role;
import com.aviator.island.entity.sys.User;
import com.aviator.island.service.AbstractBaseService;
import com.aviator.island.service.UserService;
import com.aviator.island.shiro.ShiroEncryptUserUtil;
import com.aviator.island.utils.CriteriaBuilder;
import com.aviator.island.utils.SearchConditional;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by aviator_ls on 2018/7/18.
 */
@Service
public class UserServiceImpl extends AbstractBaseService<User> implements UserService<User> {
    @Autowired
    private UserDao<User> userDao;

    public User getUserByUserName(String userName) {
        if (StringUtils.isBlank(userName)) {
            throw new IllegalArgumentException();
        }
        DetachedCriteria criteria = CriteriaBuilder.newDetachedCriteriaBuilder(this.entityClass).andConditional("userName", userName, SearchConditional.EQ).build();
        List<User> result = userDao.findByCriteria(criteria);
        if (!CollectionUtils.isEmpty(result)) {
            return result.get(0);
        }
        return null;
    }

    public User getUserById(String id) {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException();
        }
        return userDao.get(id);
    }

    public User register(User user) {
        if (user == null || StringUtils.isBlank(user.getUserName())) {
            throw new IllegalArgumentException();
        }
        User dbUser = this.getUserByUserName(user.getUserName());
        if (dbUser == null) {
            User encryptUser = ShiroEncryptUserUtil.encryptUser(user);
            user.setPassword(encryptUser.getPassword());
            user.setSalt(encryptUser.getSalt());
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            Serializable id = userDao.save(user);
            user.setId(id.toString());
            return user;
        }
        return null;
    }

    public void lockedUser(String userName) {
        if (StringUtils.isBlank(userName)) {
            throw new IllegalArgumentException();
        }
        User user = this.getUserByUserName(userName);
        user.setLocked(User.USER_LOCK.LOCK);
        userDao.update(user);
    }

    public void unLockedUser(String userName) {
        if (StringUtils.isBlank(userName)) {
            throw new IllegalArgumentException();
        }
        User user = this.getUserByUserName(userName);
        user.setLocked(User.USER_LOCK.UNLOCK);
        userDao.update(user);
    }

    @Override
    public Set<Role> findRoleSetByUserName(String userName) {
        if (StringUtils.isBlank(userName)) {
            throw new IllegalArgumentException();
        }
        User user = this.getUserByUserName(userName);
        if (user == null) {
            logger.error("user {} is not exist!", userName);
            return null;
        }
        return user.getRoleSet();
    }

    @Override
    public Set<Resource> findResourceSetByUserName(String userName) {
        if (StringUtils.isBlank(userName)) {
            throw new IllegalArgumentException();
        }
        Set<Resource> resourceSet = new HashSet<>();
        Set<Role> roleSet = findRoleSetByUserName(userName);
        if (roleSet != null) {
            for (Role role : roleSet) {
                resourceSet.addAll(role.getResourceSet());
            }
            return resourceSet;
        }
        return null;
    }

    @Override
    public Set<String> findRoleStrSetByUserName(String userName) {
        if (StringUtils.isBlank(userName)) {
            throw new IllegalArgumentException();
        }
        User user = this.getUserByUserName(userName);
        if (user != null) {
            Set<Role> roleSet = user.getRoleSet();
            if (!CollectionUtils.isEmpty(roleSet)) {
                return roleSet.stream().map(role -> role.getName()).collect(Collectors.toSet());
            }
        }
        return null;
    }

    @Override
    public Set<String> findResourceStrSetByUserName(String userName) {
        if (StringUtils.isBlank(userName)) {
            throw new IllegalArgumentException();
        }
        Set<Role> roleSet = findRoleSetByUserName(userName);
        if (!CollectionUtils.isEmpty(roleSet)) {
            return roleSet.stream().flatMap(role -> role.getResourceSet().stream().map(resource -> resource.getName())).collect(Collectors.toSet());
        }
        return null;
    }

    @Override
    protected BaseDao getBaseDao() {
        return this.userDao;
    }

}
