package ua.org.tees.yarosh.tais.homework;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.interceptor.KeyGenerator;

public class CachingConfiguration implements CachingConfigurer {
    @Override
    public CacheManager cacheManager() {
        return null;
    }

    @Override
    public KeyGenerator keyGenerator() {
        return null;
    }
}
