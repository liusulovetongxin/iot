package com.qf.iottenant.openfeign;

import com.dc3.common.bean.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iottenant.openfeign
 * @Description:
 * @Date 2022/7/14 21:56
 */
//@FeignClient(value = "iotuser")
@ReactiveFeignClient(name = "iotuser")
public interface Dc3UserOpenfeign {
    @GetMapping("/user/info/{id}")
    Mono<R> findById(@PathVariable String id);
}
