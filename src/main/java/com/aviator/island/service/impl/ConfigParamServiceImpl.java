package com.aviator.island.service.impl;

import com.aviator.island.dao.BaseDao;
import com.aviator.island.dao.ConfigParamDao;
import com.aviator.island.entity.po.ConfigParam;
import com.aviator.island.service.AbstractBaseService;
import com.aviator.island.service.ConfigParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by aviator_ls on 2018/8/26.
 */
@Service
public class ConfigParamServiceImpl extends AbstractBaseService<ConfigParam> implements ConfigParamService<ConfigParam> {

    @Autowired
    private ConfigParamDao<ConfigParam> configParamDao;

    @Override
    protected BaseDao<ConfigParam> getBaseDao() {
        return this.configParamDao;
    }
}
