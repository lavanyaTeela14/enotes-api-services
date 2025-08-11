package com.example.enotes.controller;

import com.example.enotes.endpoint.CacheEndpoint;
import com.example.enotes.service.CacheService;
import com.example.enotes.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class CacheController implements CacheEndpoint {

    @Autowired
    private CacheService cacheService;

    @Override
    public ResponseEntity<?> getAllCache() {
        Collection<String> cache = cacheService.getCache();
        return CommonUtil.createBuildResponse(cache, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getCacheName(String cache_name) {
        Cache cacheName = cacheService.getCacheName(cache_name);
        return CommonUtil.createBuildResponse(cacheName,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> removeAllCache() {
        cacheService.removeAllCaches();
        return CommonUtil.createBuildResponseMessage("Removed all caches",HttpStatus.OK);
    }
}
