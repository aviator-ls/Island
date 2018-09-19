package com.aviator.island.service;

import com.aviator.island.BaseJunit4Test;
import com.aviator.island.entity.po.LoginLog;
import com.aviator.island.entity.sys.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by aviator_ls on 2018/7/20.
 */
public class LoginLogServiceTest extends BaseJunit4Test {

    @Autowired
    private LoginLogService loginLogService;

    @Test
    public void save(){
        LoginLog loginLog = new LoginLog();
        User user = new User();
        user.setId("11");
        loginLog.setIp("111");
        loginLog.setLoginDate(new Date());
        loginLog.setUser(user);
        loginLogService.recordUserLogin(loginLog);
    }
}
