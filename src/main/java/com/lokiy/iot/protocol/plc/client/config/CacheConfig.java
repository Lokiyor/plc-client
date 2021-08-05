package com.lokiy.iot.protocol.plc.client.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.apache.plc4x.java.api.PlcConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Lokiy
 * @Date 2021/7/21 19:25
 * @Description 缓存配置类
 */
@Configuration
public class CacheConfig {


    @Bean("plcCache")
    public Cache<String, PlcConnection> plcCache(){
        return Caffeine.newBuilder()
                .build();
    }
}
