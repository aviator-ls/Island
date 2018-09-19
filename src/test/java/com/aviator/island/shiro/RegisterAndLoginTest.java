package com.aviator.island.shiro;

import com.aviator.island.BaseJunit4Test;
import com.aviator.island.entity.sys.User;
import com.aviator.island.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by 18057046 on 2018/7/27.
 */
public class RegisterAndLoginTest extends BaseJunit4Test{

    @Autowired
    private UserService userService;

    @Test
    public void register(){
        User user = new User();
        user.setUserName("root");
        user.setPassword("123456");
        userService.register(user);
    }
}
