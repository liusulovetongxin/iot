package com.qf.iotcache.config;

import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotcache.config
 * @Description: 我自己自定义的redis序列化方式
 * @Date 2022/7/13 16:23
 */
@Component
public class MyRedisSerializationContext implements RedisSerializationContext {
    /**
     * 这个是string的序列化方式
     */
    private StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

    /**
     * json格式的序列化方式
     */
    private GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();

    /**
     * 返回key的序列化方式，这个key是针对string,list,set,zset
     * @return
     */
    @Override
    public SerializationPair getKeySerializationPair() {

        return SerializationPair.fromSerializer(stringRedisSerializer);
    }

    /**
     * 返回value的序列化方式，这个key是针对string,list,set,zset
     * @return
     */
    @Override
    public SerializationPair getValueSerializationPair() {
        return SerializationPair.fromSerializer(genericJackson2JsonRedisSerializer);
    }

    /**
     * 针对hash的key的序列化方式
     * @return
     */
    @Override
    public SerializationPair<String> getStringSerializationPair() {
        return SerializationPair.fromSerializer(stringRedisSerializer);
    }
    /**
     * 针对hash的value的序列化方式
     * @return
     */
    @Override
    public SerializationPair getHashValueSerializationPair() {
        return SerializationPair.fromSerializer(genericJackson2JsonRedisSerializer);
    }

    /**
     * 针对string的序列化方式
     * @return
     */
    @Override
    public SerializationPair getHashKeySerializationPair() {
        return SerializationPair.fromSerializer(stringRedisSerializer);
    }
}
