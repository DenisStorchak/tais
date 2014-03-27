package ua.org.tees.yarosh.tais.core.common.properties;

import org.springframework.beans.factory.annotation.Value;

public class RedisProperties {
    @Value("${redis.host}")
    private String host;
    @Value("${redis.port}")
    private Integer port;
    @Value("${redis.pool}")
    private Boolean usePool;
    @Value("${redis.password}")
    private String password;
    @Value("${redis.enable}")
    private Boolean enabled;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Boolean getUsePool() {
        return usePool;
    }

    public void setUsePool(Boolean usePool) {
        this.usePool = usePool;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
