package com.aviator.island.cache.caffeine.user;

import com.aviator.island.utils.CacheUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;

import java.lang.annotation.*;

/**
 * Created by aviator_ls on 2018/7/24.
 */
@Caching(
        evict = {
                @CacheEvict(value = CacheUtil.CACHE_NAME.USER_CACHE, key = "#user.name"),
                @CacheEvict(value = CacheUtil.CACHE_NAME.USER_CACHE, key = "#user.id")
        }
)
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface UserCacheEvict {
}
