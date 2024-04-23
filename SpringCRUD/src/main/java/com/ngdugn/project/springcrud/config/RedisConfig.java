package com.ngdugn.project.springcrud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

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
                redisTemplate.setValueSerializer(new GenericToStringSerializer<>(Object.class));
                return redisTemplate;
            }
        }





