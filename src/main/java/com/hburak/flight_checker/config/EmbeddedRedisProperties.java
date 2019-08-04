package com.hburak.flight_checker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmbeddedRedisProperties {
    private int port;
    private String host;

    public EmbeddedRedisProperties(@Value("${spring.redis.port}") int port, @Value("${spring.redis.host}") String host) {
        this.port = port;
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
