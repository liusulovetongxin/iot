package com.qf.feign;

import com.dc3.common.bean.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.feign
 * @Description:
 * @Date 2022/7/16 14:48
 */
@ReactiveFeignClient(value = "iot-tenant")
public interface TenantFeign {

    @GetMapping("/tenant/info/{id}")
    Mono<R> findById(@PathVariable String id);

    @GetMapping("/tenant/findusers/{tenantId}")
    Mono<R> findByIdIn(@PathVariable String tenantId);
}
