package ua.org.tees.yarosh.tais.core.common.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Timur Yarosh
 *         Date: 27.03.14
 *         Time: 22:48
 */
@Service
public class CacheProperties {
    @Value("${cache.enable}")
    private Boolean cacheEnabled;
    @Autowired
    private RedisProperties redisProperties;

    public Boolean isCacheEnabled() {
        return cacheEnabled;
    }

    public void setCacheEnabled(Boolean cacheEnabled) {
        this.cacheEnabled = cacheEnabled;
    }

    public RedisProperties getRedisProperties() {
        return redisProperties;
    }

    public void setRedisProperties(RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
    }
}
