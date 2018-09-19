package com.aviator.island.service;

import com.aviator.island.entity.po.LoginLog;

/**
 * Created by aviator_ls on 2018/7/18.
 */
public interface LoginLogService<T> extends BaseService<T> {
    void recordUserLogin(LoginLog loginLog);
}
