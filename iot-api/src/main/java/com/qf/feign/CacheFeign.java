package com.qf.feign;

import com.dc3.common.bean.R;
import org.springframework.web.bind.annotation.*;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

import java.util.Set;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.feign
 * @Description:
 * @Date 2022/7/19 16:26
 */
@ReactiveFeignClient(value = "zck-iot-cache")
 public interface CacheFeign {

    @PostMapping("/cache/string/set")
    Mono<R> set(@RequestParam String key,@RequestParam String value);

    @GetMapping("/cache/string/get")
    Mono<R> get(@RequestParam String key);
    @PostMapping("/cache/set/exptime")
    Mono<R> setExpTime(@RequestParam String key,@RequestParam(required = true) Long expTime);

    @PostMapping("/cache/sadd/{key}")
    Mono<R> sAdd(@PathVariable String key, @RequestBody Set<String> vSet);

    @GetMapping ("/cache/smember/{key}")
    Mono<R> sMember(@PathVariable String key);

    @PostMapping("/cache/keys/delete")
    Mono<R> deleteKeys(@RequestBody Set<String> keys);
}
