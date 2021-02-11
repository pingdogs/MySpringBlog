package com.myblog.myblog.config;

import java.lang.reflect.Method;
import java.time.Duration;

import org.hibernate.SessionFactory;
import org.hibernate.boot.internal.SessionFactoryBuilderImpl;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.orm.hibernate5.support.OpenSessionInViewFilter;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;

@Configuration
@EnableCaching
public class RedisConfiguration extends CachingConfigurerSupport {

	@SuppressWarnings("deprecation")
	@Bean
	public JedisConnectionFactory redisConnectionFactory() {
		JedisConnectionFactory factory = new JedisConnectionFactory();
		factory.setPassword("Kai12348");
		return factory;
	}
	
	// key值命名
	@Bean
	public KeyGenerator myredisKeyGenerator() { 
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder sb = new StringBuilder();
               sb.append(target.getClass().getName());
				sb.append(method.getName());
				for (Object obj : params) {
					sb.append(obj.toString());
				}
				
				return sb.toString();
			}
		};
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(factory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
		
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

	@Bean
	public CacheManager cacheManager(RedisConnectionFactory factory) {

		RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair
				.fromSerializer(new GenericJackson2JsonRedisSerializer());
		RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
				.serializeValuesWith(pair) // 序列化方式
				.entryTtl(Duration.ofHours(1)); // 過期時間

		return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(factory))
				.cacheDefaults(defaultCacheConfig).build();

	}
	

}