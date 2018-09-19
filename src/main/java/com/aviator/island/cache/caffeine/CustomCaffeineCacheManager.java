package com.aviator.island.cache.caffeine;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.CaffeineSpec;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by aviator_ls on 2018/7/24.
 */
public class CustomCaffeineCacheManager implements InitializingBean,CacheManager  {
    private Caffeine<Object, Object> cacheBuilder;

    private final ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap(16);

    private boolean dynamic = true;

    private Collection<String> cacheNames;

    @Nullable
    private CacheLoader<Object, Object> cacheLoader;

    private boolean allowNullValues = true;

    private long maximumSize = -1L;

    private long maximumWeight = -1L;

    private long expireAfterWriteSeconds = -1L;

    private long expireAfterAccessSeconds = -1L;

    public CustomCaffeineCacheManager() {
    }

    public CustomCaffeineCacheManager(String... cacheNames) {
        this.setCacheNames(Arrays.asList(cacheNames));
    }

    public void setMaximumSize(long maximumSize) {
        this.maximumSize = maximumSize;
    }

    public void setExpireAfterWriteSeconds(long expireAfterWriteSeconds) {
        this.expireAfterWriteSeconds = expireAfterWriteSeconds;
    }

    public void setExpireAfterAccessSeconds(long expireAfterAccessSeconds) {
        this.expireAfterAccessSeconds = expireAfterAccessSeconds;
    }

    public void setMaximumWeight(long maximumWeight) {
        this.maximumWeight = maximumWeight;
    }

    public void setCacheNames(@Nullable Collection<String> cacheNames) {
        if (cacheNames != null) {
            this.cacheNames = cacheNames;
            this.dynamic = false;
        } else {
            this.dynamic = true;
        }

    }

    public void setCaffeine(Caffeine<Object, Object> caffeine) {
        Assert.notNull(caffeine, "Caffeine must not be null");
        this.doSetCaffeine(caffeine);
    }

    public void setCaffeineSpec(CaffeineSpec caffeineSpec) {
        this.doSetCaffeine(Caffeine.from(caffeineSpec));
    }

    public void setCacheSpecification(String cacheSpecification) {
        this.doSetCaffeine(Caffeine.from(cacheSpecification));
    }

    public void setCacheLoader(CacheLoader<Object, Object> cacheLoader) {
        if (!ObjectUtils.nullSafeEquals(this.cacheLoader, cacheLoader)) {
            this.cacheLoader = cacheLoader;
            this.refreshKnownCaches();
        }

    }

    public void setAllowNullValues(boolean allowNullValues) {
        if (this.allowNullValues != allowNullValues) {
            this.allowNullValues = allowNullValues;
            this.refreshKnownCaches();
        }

    }

    public boolean isAllowNullValues() {
        return this.allowNullValues;
    }

    @Override
    public Collection<String> getCacheNames() {
        return Collections.unmodifiableSet(this.cacheMap.keySet());
    }

    @Nullable
    @Override
    public Cache getCache(String name) {
        Cache cache = (Cache) this.cacheMap.get(name);
        if (cache == null && this.dynamic) {
            ConcurrentMap var3 = this.cacheMap;
            synchronized (this.cacheMap) {
                cache = (Cache) this.cacheMap.get(name);
                if (cache == null) {
                    cache = this.createCaffeineCache(name);
                    this.cacheMap.put(name, cache);
                }
            }
        }

        return cache;
    }

    protected Cache createCaffeineCache(String name) {
        return new CaffeineCache(name, this.createNativeCaffeineCache(name), this.isAllowNullValues());
    }

    protected com.github.benmanes.caffeine.cache.Cache<Object, Object> createNativeCaffeineCache(String name) {
        return (com.github.benmanes.caffeine.cache.Cache) (this.cacheLoader != null ? this.cacheBuilder.build(this.cacheLoader) : this.cacheBuilder.build());
    }

    private void doSetCaffeine(Caffeine<Object, Object> cacheBuilder) {
        if (!ObjectUtils.nullSafeEquals(this.cacheBuilder, cacheBuilder)) {
            this.cacheBuilder = cacheBuilder;
            this.refreshKnownCaches();
        }

    }

    private void refreshKnownCaches() {
        Iterator var1 = this.cacheMap.entrySet().iterator();

        while (var1.hasNext()) {
            Map.Entry entry = (Map.Entry) var1.next();
            entry.setValue(this.createCaffeineCache((String) entry.getKey()));
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Caffeine<Object, Object> caffeineBuilder = Caffeine.newBuilder();
        if (this.maximumSize >= 0L) {
            caffeineBuilder = caffeineBuilder.maximumSize(this.maximumSize);
        }
        if (this.maximumWeight >= 0L) {
            caffeineBuilder = caffeineBuilder.maximumWeight(this.maximumWeight);
        }
        if (this.expireAfterWriteSeconds >= 0L) {
            caffeineBuilder = caffeineBuilder.expireAfterWrite(this.expireAfterWriteSeconds, TimeUnit.SECONDS);
        }
        if (this.expireAfterAccessSeconds >= 0L) {
            caffeineBuilder = caffeineBuilder.expireAfterAccess(this.expireAfterAccessSeconds, TimeUnit.SECONDS);
        }
        this.cacheBuilder = caffeineBuilder;
        if (!CollectionUtils.isEmpty(this.cacheNames)) {
            Iterator var2 = cacheNames.iterator();

            while (var2.hasNext()) {
                String name = (String) var2.next();
                this.cacheMap.put(name, this.createCaffeineCache(name));
            }
        }
    }


}
