package com.example.enotes.service.impl;

import com.example.enotes.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    private CacheManager cacheManager;

    @Override
    public Collection<String> getCache() {
        Collection<String> cacheNames = cacheManager.getCacheNames();
        for(String cacheName : cacheNames)
        {
            Cache cache= cacheManager.getCache(cacheName);
            log.info("Cache Name : "+cacheName);
        }
        return cacheNames;
    }

    @Override
    public Cache getCacheName(String cacheName) {
        Cache cache=cacheManager.getCache(cacheName);
        return cache;
    }

    @Override
    public void removeAllCaches() {
        Collection<String> cacheNames = cacheManager.getCacheNames();
        for(String cacheName : cacheNames)
        {
            Cache cache=cacheManager.getCache(cacheName);
            log.info("Cache Name : "+cacheName);
            cache.clear();
        }

    }

    @Override
    public void removeCacheByName(List<String> cacheNames) {
        for(String cacheName : cacheNames)
        {
            Cache cache=cacheManager.getCache(cacheName);
            log.info("Cache Name : {}",cache);
            cache.clear();
        }
    }
}
