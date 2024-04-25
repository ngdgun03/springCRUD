package com.ngdugn.project.springcrud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
        @EnableCaching
        public class RedisConfig {

            private final RedisProperties redisProperties;

            @Autowired
            public RedisConfig(RedisProperties redisProperties) {
                this.redisProperties = redisProperties;
            }

            @Bean
            public JedisConnectionFactory jedisConnectionFactory() {
                RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
                config.setHostName(redisProperties.getHost());
                config.setPort(redisProperties.getPort()); // Sử dụng trực tiếp giá trị port kiểu int
                return new JedisConnectionFactory(config);
            }

            @Bean
            public RedisTemplate<String, Object> redisTemplate(){
                RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
                redisTemplate.setConnectionFactory(jedisConnectionFactory());
                redisTemplate.setKeySerializer(new StringRedisSerializer());
                redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
                return redisTemplate;
            }

    @Bean
    public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForList();
    }
        }





