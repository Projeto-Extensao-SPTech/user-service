package com.dog_feliz.user_service.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.Map;

@Configuration
public class RedisCacheConfig {

    @Bean
    public RedisCacheManager redisCacheManager(
            RedisConnectionFactory cf, ObjectMapper objectMapper) {

        ObjectMapper cacheMapper = objectMapper.copy();
        cacheMapper.activateDefaultTyping(
                cacheMapper.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.PROPERTY
        );

        var json = new GenericJackson2JsonRedisSerializer(cacheMapper);

        var defaults = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(json))
                .disableCachingNullValues()
                .prefixCacheNameWith("app::")
                .entryTtl(Duration.ofMinutes(5));

        var perCache = Map.of(
                "notifications", defaults.entryTtl(Duration.ofMinutes(10)),
                "fairs", defaults.entryTtl(Duration.ofMinutes(5))
        );

        return RedisCacheManager.builder(cf)
                .cacheDefaults(defaults)
                .withInitialCacheConfigurations(perCache)
                .build();
    }
}
