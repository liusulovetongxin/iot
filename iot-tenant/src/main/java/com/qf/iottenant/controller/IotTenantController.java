package com.qf.iottenant.controller;

import com.dc3.common.bean.R;
import com.qf.iottenant.pojo.Dc3Tenant;
import com.qf.iottenant.pojo.Dc3TenantBind;
import com.qf.iottenant.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iottenant.controller
 * @Description:
 * @Date 2022/7/14 16:26
 */
@RestController
@RequestMapping("/tenant")
public class IotTenantController {
    private TenantService tenantService;

    @Autowired
    public void setTenantService(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PostMapping("")
    public Mono<R<Object>> addTenant(@RequestBody Mono<Dc3Tenant> tenantMono){
        return tenantService.addTenant(tenantMono);
    }
    @GetMapping("/info/{id}")
    public Mono<R<Dc3Tenant>> findById(@PathVariable String id){
        return tenantService.findById(id).map(dc3Tenant -> R.ok(dc3Tenant)).defaultIfEmpty(R.fail("没有查询到数据"));
    }

    @GetMapping("/list/{name}")
    public Mono<R> findByName(@PathVariable String name){
        return tenantService.findByName(name).collectList().map(dc3Tenants -> R.ok(dc3Tenants));
    }

    @PostMapping("/update")
    public Mono<R<Object>> updateTenant(@RequestBody Mono<Dc3Tenant> tenantMono){
        return tenantService.update(tenantMono);
    }

    @PostMapping("/bind")
    public Mono<R<Object>> bindTenant2User(@RequestBody Mono<Dc3TenantBind> tenantBindMono){
        return tenantService.bindTenant2User(tenantBindMono);
    }
}
