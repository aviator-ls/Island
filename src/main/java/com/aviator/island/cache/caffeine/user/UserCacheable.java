package com.aviator.island.cache.caffeine.user;

import com.aviator.island.utils.CacheUtil;
import org.springframework.cache.annotation.Cacheable;

import java.lang.annotation.*;

/**
 * Created by 18057046 on 2018/7/24.
 */
@Cacheable(cacheNames = CacheUtil.CACHE_NAME.USER_CACHE)
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface UserCacheable {
}
