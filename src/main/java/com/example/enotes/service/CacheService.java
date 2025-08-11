package com.example.enotes.service;

import org.springframework.cache.Cache;

import java.util.Collection;
import java.util.List;

public interface CacheService {
    public Collection<String> getCache();
    public Cache getCacheName(String CacheName);
    public void removeAllCaches();
    public void removeCacheByName(List<String> cacheNames);
}
