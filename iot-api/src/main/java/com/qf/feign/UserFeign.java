package com.qf.feign;

import com.dc3.common.bean.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.feign
 * @Description:
 * @Date 2022/7/15 19:31
 */
@ReactiveFeignClient(value = "iotuser")
public interface UserFeign {

    @GetMapping("/user/info/{id}")
    Mono<R> findById(@PathVariable String id);

    @PostMapping("/user/list")
    public Mono<R> findByIdIn(@RequestBody List<String> ids);
}
