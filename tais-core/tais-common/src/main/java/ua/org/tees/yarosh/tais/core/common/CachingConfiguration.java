package ua.org.tees.yarosh.tais.core.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import ua.org.tees.yarosh.tais.core.common.properties.CacheProperties;

import java.util.Arrays;

@Configuration
@Import(ExternalConfiguration.class)
@EnableCaching
public class CachingConfiguration implements CachingConfigurer {

    @Autowired
    private CacheProperties cacheProperties;

    @Override
    @Bean
    public CacheManager cacheManager() {
        if (cacheProperties.getRedisProperties().isEnabled() && cacheProperties.isCacheEnabled()) {
            RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate());
            redisCacheManager.setUsePrefix(true);
            return redisCacheManager;
        } else if (cacheProperties.isCacheEnabled()) {
            SimpleCacheManager cacheManager = new SimpleCacheManager();
            ConcurrentMapCache groupsCache = new ConcurrentMapCache(CacheNames.GROUPS);
            ConcurrentMapCache registrantsCache = new ConcurrentMapCache(CacheNames.REGISTRANTS);
            cacheManager.setCaches(Arrays.asList(groupsCache, registrantsCache));
            return cacheManager;
        } else {
            return new NoOpCacheManager();
        }
    }

    @Override
    @Bean
    public KeyGenerator keyGenerator() {
        return new SimpleKeyGenerator();
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        connectionFactory.setHostName(cacheProperties.getRedisProperties().getHost());
        connectionFactory.setPort(cacheProperties.getRedisProperties().getPort());
        connectionFactory.setUsePool(cacheProperties.getRedisProperties().getUsePool());
        if (cacheProperties.getRedisProperties().getPassword() != null
                && !cacheProperties.getRedisProperties().getPassword().isEmpty()) {

            connectionFactory.setPassword(cacheProperties.getRedisProperties().getPassword());
        }
        return connectionFactory;
    }

    @Bean
    public RedisTemplate redisTemplate() {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }
}
