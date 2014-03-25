package ua.org.tees.yarosh.tais.core.user.mgmt.configuration;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

import static ua.org.tees.yarosh.tais.core.user.mgmt.configuration.CacheNames.REGISTRATION;

@Configuration
@EnableCaching
public class CachingConfiguration implements CachingConfigurer {
    @Override
    public CacheManager cacheManager() {
        ArrayList<Cache> caches = new ArrayList<>();
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(caches);

        caches.add(new ConcurrentMapCache(REGISTRATION));

        return cacheManager;
    }

    @Override
    public KeyGenerator keyGenerator() {
        return new SimpleKeyGenerator();
    }
}
