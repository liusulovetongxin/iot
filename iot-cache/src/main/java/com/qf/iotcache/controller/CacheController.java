package com.qf.iotcache.controller;

import com.dc3.common.bean.R;
import com.qf.iotcache.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Set;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotcache.controller
 * @Description: 控制器类
 * @Date 2022/7/13 16:42
 */
@RestController
@RequestMapping("/cache")
public class CacheController {
    private CacheService cacheService;

    @Autowired
    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @PostMapping("/string/set")
    public Mono<R> set(String key,String value){
        return cacheService.setValue(key, value).map(bool -> R.ok(bool));
    }

    @GetMapping("/string/get")
    public Mono<R> get(String key){
        return cacheService.getValue(key).map(str->R.ok(((Object) str)));
    }

    @PostMapping("/set/exptime")
    public Mono<R> setExpTime(String key,@RequestParam(required = true) Long expTime){
        return cacheService.setExpTime(key, expTime).map(result -> R.ok(result));
    }

    @PostMapping("/increment")
    public Mono<R> increment(String key,@RequestParam(defaultValue = "1") long delta){
        return cacheService.increment(key,delta).map(result-> R.ok(result));
    }
    @PostMapping("/string/setnx")
    public Mono<R> setNx(String key,String value){
        return cacheService.setNx(key, value).map(result->R.ok(result));
    }


    @PostMapping("/list/set")
    public Mono<R> setListValue(Long index, String key, String value){
        return cacheService.setListValue(index, key, value).map(result->R.ok(result));
    }
    @PostMapping("/list/setleft")
    public Mono<R> setLeftValue(String key, String value){
        return cacheService.setLeftValue(key, value).map(result-> R.ok(result));
    }
    @PostMapping("/list/setright")
    public Mono<R> setRightValue(String key, String value){
        return cacheService.setRightValue(key, value).map(result-> R.ok(result));
    }

    @PostMapping("/list/setleftall")
    public Mono<R> setLeftAll(String key, String ...values){
        return cacheService.setLeftAll(key, values).map(result-> R.ok(result));
    }
    @PostMapping("/list/setrightall")
    public Mono<R> setRightAll(String key, String ...values){
        return cacheService.setRightAll(key, values).map(result-> R.ok(result));
    }

    @PostMapping("/list/getleft")
    public Mono<R> getLeftValue(String key){
        return cacheService.getLeftValue(key).map(result-> R.ok(result));
    }
    @PostMapping("/list/getright")
    public Mono<R> getRightValue(String key){
        return cacheService.getRightValue(key).map(result-> R.ok(result));
    }
    @PostMapping("/set/set")
    public Mono<R> setSet(String key,String ...values){
        return cacheService.setSet(key, values).map(result->R.ok(result));
    }
    @PostMapping("/set/get")
    public Mono<R> getSet(String key,@RequestParam(defaultValue = "1")Long count){
        return cacheService.getSet(key,count).map(result->R.ok(result));
    }

    @GetMapping("/set/getall")
    public Mono<R> getSetAll(String key){
        return cacheService.getSetAll(key).map(result->R.ok(result));
    }

    @PostMapping("/map/set")
    public Mono<R> setMap(String key,String field,String value){
        return cacheService.hSet(key, field, value).map(result->R.ok(result));
    }

    @PostMapping("/map/setall")
    public Mono<R> setMapAll(String key,@RequestBody Map data){
        return cacheService.hMSet(key, data).map(result -> R.ok(result));
    }
    @GetMapping("/map/get")
    public Mono<R> getMap(String key,String field){
        return cacheService.hGet(key, field).map(result-> R.ok(result));
    }
    @GetMapping("/map/getall")
    public Mono<R> getMapAll(String key){
        return cacheService.hGetAll(key).map(result-> R.ok(result));
    }
    @GetMapping("/map/scan")
    public Mono<R> getMapScan(String key){
        return cacheService.hScan(key).map(result-> R.ok(result));
    }

    @DeleteMapping("/delete")
    public Mono<R> delete(String key){
        return cacheService.delete(key).map(result->R.ok(result));
    }

    @PostMapping("/sadd/{key}")
    public Mono<R> sAdd(@PathVariable String key, @RequestBody Set<String> vSet){
        return cacheService.sAdd(key, vSet.toArray(new String[]{})).map(result->R.ok(result));
    }

    @GetMapping ("/smember/{key}")
    public Mono<R> sMember(@PathVariable String key){
        return cacheService.getSetAll(key).map(result->R.ok(result));
    }

    @PostMapping("/keys/delete")
    public Mono<R> deleteKeys(@RequestBody Set<String> keys){
        return cacheService.deleteKeys(keys.toArray(new String[]{})).map(result->R.ok(result));
    }




}
