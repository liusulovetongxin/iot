package com.qf.iottenant.service;

import com.dc3.common.bean.R;
import com.qf.iottenant.pojo.Dc3Tenant;
import com.qf.iottenant.pojo.Dc3TenantBind;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iottenant.service
 * @Description:
 * @Date 2022/7/14 11:38
 */

public interface TenantService {
    Mono<R<Object>> addTenant(Mono<Dc3Tenant> tenantMono);

    Mono<Dc3Tenant> findById(String id);

    Flux<Dc3Tenant> findByName(String name);

    Mono<R<Object>> update(Mono<Dc3Tenant> tenantMono);
    Mono<R<Object>> updateDc3Tenant(Mono<Dc3Tenant> tenantMono);

    Mono<R<Object>> bindTenant2User(Mono<Dc3TenantBind> tenantBindMono);

    Mono<Dc3Tenant> findByUsers(String tenantId);
}
