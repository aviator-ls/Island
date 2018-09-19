package com.aviator.island.cache.caffeine.user;

import com.aviator.island.utils.CacheUtil;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;

import java.lang.annotation.*;

/**
 * Created by aviator_ls on 2018/7/24.
 */
@Caching(
        put = {
                @CachePut(value = CacheUtil.CACHE_NAME.USER_CACHE, key = "#result.id", condition = "#result != null"),
                @CachePut(value = CacheUtil.CACHE_NAME.USER_CACHE, key = "#result.name", condition = "#result != null")
        }
)
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface UserCachePut {
}
