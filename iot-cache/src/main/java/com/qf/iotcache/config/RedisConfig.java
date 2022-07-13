package com.qf.iotcache.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotcache.config
 * @Description: redis配置
 * @Date 2022/7/13 16:18
 */
// 配置是要加这个注解的，否则不会执行
@Configuration
public class RedisConfig {
    // 在@configuration 注解修饰的类中如果还有@bean注解的话，@value注解不生效
    @Value("${name}")
    private String name;

    /**
     * 创建模板对象，使用我们自己的序列化方式
     * @param factory
     * @param context
     * @return
     */
    @Bean
    public ReactiveRedisTemplate reactiveRedisTemplate(ReactiveRedisConnectionFactory factory,MyRedisSerializationContext context){
        return new ReactiveRedisTemplate(factory, context);
//        return new ReactiveRedisTemplate(factory, )
    }
}
