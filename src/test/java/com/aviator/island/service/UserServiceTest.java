package com.aviator.island.service;

import com.aviator.island.BaseJunit4Test;
import com.aviator.island.entity.sys.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.Set;

/**
 * Created by 18057046 on 2018/7/18.
 */
public class UserServiceTest extends BaseJunit4Test {

    @Autowired
    private UserService userService;

    @Test
    public void getUserByName(){
        String exName = "111";
        User user = userService.getUserByUserName(exName);
        Assert.notNull(user, "name " + exName + " exist, user should not be null");
        String nexName = "222";
        user = userService.getUserByUserName(nexName);
        Assert.isNull(user, "name " + nexName + " not exist, user should be null");
    }

    @Test
    public void getUserById(){
        String exId = "8ab1878164cbf3540164cbf3a8070000";
        User user = userService.getUserById(exId);
        Assert.notNull(user, "id " + exId + " exist, user should not be null");
        String nexId = "222";
        user = userService.getUserById(nexId);
        Assert.isNull(user, "id " + nexId + " not exist, user should be null");
    }

    @Test
    public void lockedAndUnLockUser(){
        String name = "111";
        userService.lockedUser(name);
        User user = userService.getUserByUserName(name);
        Assert.isTrue(user.getLocked() == 1, "cant lock!");
        userService.unLockedUser(name);
        user = userService.getUserByUserName(name);
        Assert.isTrue(user.getLocked() == 0, "cant unLock!");
    }

    @Test
    public void findRoleStrSetByUserName(){
        String name = "111";
        Set<String> roleNameSet = userService.findRoleStrSetByUserName(name);
        Assert.isNull(roleNameSet, "user " + name + " not have role");
    }
}
