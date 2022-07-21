package com.qf.feign;

import com.dc3.common.bean.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.feign
 * @Description:
 * @Date 2022/7/19 16:26
 */
@FeignClient(value = "zck-iot-cache")
public interface CacheFeign {

    @PostMapping("/cache/string/set")
    R set(@RequestParam String key,@RequestParam String value);

    @GetMapping("/cache/string/get")
    R get(@RequestParam String key);
    @PostMapping("/cache/set/exptime")
    R setExpTime(@RequestParam String key,@RequestParam(required = true) Long expTime);
    @PostMapping("/cache/sadd/{key}")
    R sAdd(@PathVariable String key, @RequestBody Set<String> vSet);

    @GetMapping ("/cache/smember/{key}")
    R sMember(@PathVariable String key);

    @PostMapping("/cache/keys/delete")
    R deleteKeys(@RequestBody Set<String> keys);
}
