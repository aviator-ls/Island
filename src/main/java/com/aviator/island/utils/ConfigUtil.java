package com.aviator.island.utils;

import com.google.common.collect.Maps;
import com.aviator.island.entity.po.ConfigParam;
import com.aviator.island.service.ConfigParamService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by aviator_ls on 2018/8/26.
 */
@Component
public class ConfigUtil implements InitializingBean {

    @Autowired
    private ConfigParamService<ConfigParam> configParamService;

    private static final Map<String, ConfigParam> configMap = Maps.newHashMap();

    public static <T> T getConfigValue(String configName) {
        return getConfigValue(configName, null);
    }

    public static <T> T getConfigValue(String configName, T defaultValue) {
        ConfigParam configParam = configMap.get(configName);
        return (T) (configParam == null || StringUtils.isBlank(configParam.getValue()) ? defaultValue : configParam.getValue());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<ConfigParam> configParamList = this.configParamService.findAll();
        configParamList.forEach(configParam -> configMap.put(configParam.getName(), configParam));
    }
}
