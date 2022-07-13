package com.qf.iotcache.service.impl;

import com.qf.iotcache.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotcache.service.impl
 * @Description: 缓存业务接口的实现类
 * @Date 2022/7/13 16:17
 */
@Service
public class CacheServiceImpl implements CacheService {
    private ReactiveRedisTemplate redisTemplate;

    @Autowired
    public void setReactiveRedisTemplate(ReactiveRedisTemplate reactiveRedisTemplate) {
        this.redisTemplate = reactiveRedisTemplate;
    }

    @Override
    public Mono<Boolean> setValue(String key, Object value) {
        Assert.hasText(key, ()->{
            throw new RuntimeException("传入的数据不能为空");
        });
        if (value instanceof String){
        Assert.hasText((String) value, ()->{
            throw new RuntimeException("传入的数据不能为空");
        });}
        else {
            Assert.notNull(value, ()->{
                throw new RuntimeException("传入的数据不能为空");
            });
        }
        return redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public Mono<String> getValue(String key) {
        Assert.hasText(key, ()->{
            throw new RuntimeException("传入的数据不能为空");
        });
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public Mono<Boolean> setExpTime(String key, Long expTime) {
        Assert.hasText(key, ()->{
            throw new RuntimeException("传入的数据不能为空");
        });
        Assert.isTrue(expTime>0, ()->{
            throw new RuntimeException("生存时间设置不合理，请重新设置");
        });
        return redisTemplate.expire(key, Duration.ofMillis(expTime));
    }

    @Override
    public Mono<Long> increment(String key,long delta) {
        return redisTemplate.opsForValue().increment(key,delta);
    }

    @Override
    public Mono<Boolean> setNx(String key, String value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    @Override
    public Mono<Long> delete(String key) {
        return redisTemplate.delete(key);
    }

    @Override
    public Mono<Boolean> setListValue(Long index, String key, String value) {
        return redisTemplate.opsForList().set(key, index, value);
    }

    @Override
    public Mono<Long> setLeftValue(String key, String value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    @Override
    public Mono<Long> setRightValue(String key, String value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    @Override
    public Mono<Long> setLeftAll(String key, String... values) {
        return redisTemplate.opsForList().leftPushAll(key, values);
    }

    @Override
    public Mono<Long> setRightAll(String key, String... values) {
        return redisTemplate.opsForList().rightPushAll(key, values);
    }

    @Override
    public Mono<String> getLeftValue(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }
    @Override
    public Mono<String> getRightValue(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    @Override
    public Mono<Long> setSet(String key, String... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    @Override
    public Mono<List> getSet(String key, Long count) {
        return redisTemplate.opsForSet().pop(key, count).collectList();
    }

    @Override
    public Mono<List> getSetAll(String key) {
        return redisTemplate.opsForSet().members(key).collectList();
    }

    @Override
    public Mono<Boolean> hSet(String key, String field, String value) {
        return redisTemplate.opsForHash().put(key, field, value);
    }

    @Override
    public Mono<Boolean> hMSet(String key, Map data) {
        return redisTemplate.opsForHash().putAll(key, data);
    }

    @Override
    public Mono<String> hGet(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);

    }

    @Override
    public Mono<Map> hGetAll(String key) {
        Flux<Map.Entry<String,String>> entries = redisTemplate.opsForHash().entries(key);
        return entries.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Mono<Map> hScan(String key) {
        Flux<Map.Entry<String,String>> scan = redisTemplate.opsForHash().scan(key);
        return scan.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
