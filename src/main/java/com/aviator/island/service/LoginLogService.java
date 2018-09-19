package com.aviator.island.service;

import com.aviator.island.entity.po.LoginLog;

/**
 * Created by 18057046 on 2018/7/18.
 */
public interface LoginLogService<T> extends BaseService<T> {
    void recordUserLogin(LoginLog loginLog);
}
