package com.aviator.island.utils;

import com.aviator.island.entity.po.LoginLog;
import com.aviator.island.entity.sys.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by 18057046 on 2018/8/2.
 */
public class RecordUtil {

    public static LoginLog recordUser(HttpServletRequest request, User user) {
        return new LoginLog().setIp(request.getRemoteAddr()).setLoginDate(new Date()).setUser(user);
    }

    public static LoginLog recordUser(String ip, User user) {
        return new LoginLog().setIp(ip).setLoginDate(new Date()).setUser(user);
    }
}
