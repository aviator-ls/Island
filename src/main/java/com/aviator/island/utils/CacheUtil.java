package com.aviator.island.utils;

import org.springframework.cache.Cache;

/**
 * Created by aviator_ls on 2018/7/24.
 */
public class CacheUtil {
    public interface CACHE_NAME {
        String USER_CACHE = "userCache";
        String COMPONENT_MANAGER_CACHE = "componentManagerCache";
        String RETRY_LIMIT_CACHE_NAME = "passwordRetryCache";
    }

    public static <T> T getForCache(Cache cache, String name, Class<T> requiredType) {
        Cache.ValueWrapper valueWrapper = cache.get(name);
        if (valueWrapper != null) {
            return (T) valueWrapper.get();
        }
        return null;
    }
}
