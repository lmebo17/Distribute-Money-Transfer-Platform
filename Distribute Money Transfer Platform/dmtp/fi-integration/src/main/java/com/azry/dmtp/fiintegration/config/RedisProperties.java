package com.azry.dmtp.fiintegration.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Data
@Component
@ConfigurationProperties(prefix = "spring.cache.redis")
@EnableConfigurationProperties(RedisProperties.class)
public class RedisProperties {

    private Duration timeToLive;

}
