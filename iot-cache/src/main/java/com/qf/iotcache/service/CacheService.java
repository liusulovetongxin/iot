package com.qf.iotcache.service;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotcache.service
 * @Description: 缓存的service接口类
 * @Date 2022/7/13 16:09
 */

import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

/**
 * 缓存的业务接口，定义了一些方法
 */
public interface CacheService {
    /**
     * 设置数据
     *
     * @param key
     * @param value
     * @return
     */
    Mono<Boolean> setValue(String key, Object value);
    /**
     * 获取数据
     * @param key
     * @return
     */
    Mono<String> getValue(String key);
    /**
     * 设置生存时间
     * @param key
     * @param expTime
     * @return
     */
    Mono<Boolean> setExpTime(String key,Long expTime);

    /**
     * string类型中设置递增，delta是步长？
     * @param key
     * @param delta
     * @return
     */
    Mono<Long> increment(String key, long delta);

    /**
     * 没有就设置，返回值false和true代表设置成功和失败
     * 这玩意是可以做分布式锁的
     * @param key
     * @param value
     * @return
     */
    Mono<Boolean> setNx(String key,String value);

    /**
     * 根据key来删除
     * @param key
     * @return
     */
    Mono<Long> delete(String key);

    /**
     * 设置list INdex我不知道是啥，可能是代表着位置吧
     * @param index
     * @param key
     * @param value
     * @return
     */
    Mono<Boolean> setListValue(Long index, String key, String value);

    /**
     * 左加
     * @param key
     * @param value
     * @return
     */
    Mono<Long> setLeftValue(String key, String value);

    /**
     * 右加
     * @param key
     * @param value
     * @return
     */
    Mono<Long> setRightValue(String key, String value);

    /**
     * 左批量加
     * @param key
     * @param values
     * @return
     */
    Mono<Long> setLeftAll(String key, String ...values);
    /**
     * 右批量加
     * @param key
     * @param values
     * @return
     */
    Mono<Long> setRightAll(String key, String ...values);

    /**
     * 左取
     * @param key
     * @return
     */
    Mono<String> getLeftValue(String key);
    /**
     * 右取
     * @param key
     * @return
     */
    Mono<String> getRightValue(String key);

    /**
     * 加set
     * @param key
     * @param value
     * @return
     */
    Mono<Long> setSet(String key, String ...value);

    /**
     * 取值，根据key，count取出几个，注意，是弹栈，所以就没了数据
     * @param key
     * @param count
     * @return
     */
    Mono<List> getSet(String key,Long count);

    /**
     * 获取key的全部值
     * @param key
     * @return
     */
    Mono<List> getSetAll(String key);

    /**
     * 设置hash，通过key，field和value设置
     * @param key
     * @param field
     * @param value
     * @return
     */
    Mono<Boolean> hSet(String key, String field, String value);

    /**
     * 批量添加
     * @param key
     * @param data
     * @return
     */
    Mono<Boolean> hMSet(String key, Map data);

    /**
     * 获取，通过key和field
     * @param key
     * @param field
     * @return
     */
    Mono<String> hGet(String key,String field);

    /**
     * 通过key获取全部
     * @param key
     * @return
     */
    Mono<Map> hGetAll(String key);
    /**
     * 通过key获取全部，但是不知道和上边有什么区别
     * @param key
     * @return
     */
    Mono<Map> hScan(String key);
}
