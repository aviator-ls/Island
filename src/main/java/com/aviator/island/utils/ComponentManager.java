package com.aviator.island.utils;

import org.springframework.beans.BeansException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by 18057046 on 2018/8/13.
 */
@Component
public class ComponentManager implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Cacheable(value = CacheUtil.CACHE_NAME.COMPONENT_MANAGER_CACHE)
    public static Object getComponent(String componentName) {
        return getComponentByObj(componentName);
    }

    @Cacheable(value = CacheUtil.CACHE_NAME.COMPONENT_MANAGER_CACHE, key = "#componentClass.getName()")
    public static <T> T getComponent(Class<T> componentClass) {
        return (T) getComponentByObj(componentClass);
    }

    /**
     * 获取bean对象<br>
     * 从内部缓存中获取指定bean对象，若没有，从上下文中获取，并放入内部缓存中
     *
     * @param componentRequired bean名称或类
     * @return bean对象
     */
    private static Object getComponentByObj(Object componentRequired) {
        if (componentRequired instanceof Class) {
            return applicationContext.getBean((Class) componentRequired);
        }
        return applicationContext.getBean(componentRequired.toString());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
