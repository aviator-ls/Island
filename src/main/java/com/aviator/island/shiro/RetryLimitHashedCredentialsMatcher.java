package com.aviator.island.shiro;

import com.aviator.island.constants.WebConstants;
import com.aviator.island.utils.CacheUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by 18057046 on 2018/7/31.
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    private Cache passwordRetryCache;

    private RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache(CacheUtil.CACHE_NAME.RETRY_LIMIT_CACHE_NAME);
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String userName = (String) token.getPrincipal();
        AtomicInteger retryCount = CacheUtil.getForCache(passwordRetryCache, userName, AtomicInteger.class);
        if (retryCount == null) {
            retryCount = new AtomicInteger();
            passwordRetryCache.put(userName, retryCount);
        }
        if (retryCount.incrementAndGet() > WebConstants.USER_RETRY_COUNT) {
            throw new ExcessiveAttemptsException();
        }
        boolean result = super.doCredentialsMatch(token, info);
        if (result) {
            passwordRetryCache.evict(userName);
        }
        return result;
    }
}
