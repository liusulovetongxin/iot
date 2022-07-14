package com.qf.iottenant.service.impl;

import cn.hutool.core.lang.UUID;
import com.dc3.common.bean.R;
import com.qf.iottenant.openfeign.Dc3UserOpenfeign;
import com.qf.iottenant.pojo.Dc3Tenant;
import com.qf.iottenant.pojo.Dc3TenantBind;
import com.qf.iottenant.pojo.Dc3User;
import com.qf.iottenant.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.data.relational.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iottenant.service.impl
 * @Description:
 * @Date 2022/7/14 11:42
 */
@Service
public class TenantServiceImpl implements TenantService {
    private R2dbcEntityTemplate r2dbcEntityTemplate;

    @Autowired
    public void setR2dbcEntityTemplate(R2dbcEntityTemplate r2dbcEntityTemplate) {
        this.r2dbcEntityTemplate = r2dbcEntityTemplate;
    }

    private Dc3UserOpenfeign openfeign;

    @Autowired
    public void setOpenfeign(Dc3UserOpenfeign openfeign) {
        this.openfeign = openfeign;
    }

    @Override
    public Mono<R<Object>> addTenant(Mono<Dc3Tenant> tenantMono) {
        return tenantMono
                .filter(dc3Tenant -> StringUtils.hasText(dc3Tenant.getName()) && StringUtils.hasText(dc3Tenant.getDescription()))
                .doOnNext(dc3Tenant ->
                        dc3Tenant.setId(UUID.randomUUID().toString(true)))
                .map(dc3Tenant -> {
                    r2dbcEntityTemplate.insert(Dc3Tenant.class).into("dc3_tenant").using(dc3Tenant)
//                                    .doOnError(e -> R.fail("添加失败" + e.getMessage()))
//                                    .doOnSuccess(result -> R.ok("添加成功"));
                            .subscribe();
                    return R.ok("添加成功");
                        }
                ).defaultIfEmpty(R.fail("添加失败"))
                .doOnError(System.err::println);
    }

    @Override
    public Mono<Dc3Tenant> findById(String id) {
        return r2dbcEntityTemplate.selectOne(Query.query(Criteria.where("id").is(id)),Dc3Tenant.class)
                .doOnNext(dc3Tenant -> dc3Tenant.setUsers( openfeign.findById(id).map(r -> {
                    Dc3User data = (Dc3User) r.getData();
                    return data;
                }));
    }

    @Override
    public Flux<Dc3Tenant> findByName(String name) {
        return r2dbcEntityTemplate.select(Dc3Tenant.class).matching(Query.query(Criteria.where("name").is(name))).all();
    }

    @Override
    public Mono<R<Object>> update(Mono<Dc3Tenant> tenantMono) {
        return tenantMono.filter(dc3Tenant ->StringUtils.hasText(dc3Tenant.getId())&&StringUtils.hasText(dc3Tenant.getName())&&StringUtils.hasText(dc3Tenant.getDescription()))
                .doOnNext(dc3Tenant -> dc3Tenant.setUpdateTime(LocalDateTime.now()))
                .map(dc3Tenant -> {
                    Mono<Integer> mono = r2dbcEntityTemplate.update(Dc3Tenant.class)
                            .inTable("dc3_tenant")
                            .matching(Query.query(Criteria.where("id").is(dc3Tenant.getId())))
                            .apply(Update.update("name", dc3Tenant.getName())
                                    .set("description", dc3Tenant.getDescription())
                                    .set("update_time", dc3Tenant.getUpdateTime()));
//                    return mono.map(i-> i==1? "修改成功" : "修改失败");
//                    return ;
                    return mono.toProcessor().block() == 1 ? R.ok("更新成功"):R.fail("更新失败");
                })
                .defaultIfEmpty(R.fail("数据传输错误"));
    }

    public Mono<R<Object>> bindTenant2User(Mono<Dc3TenantBind> tenantBindMono) {
        return tenantBindMono.filter(
                tenantbind -> StringUtils.hasText(tenantbind.getTenantId())
                        && StringUtils.hasText(tenantbind.getUserId())
                        && StringUtils.hasText(tenantbind.getDescription())
        ).doOnNext(
                bindMono->bindMono.setId(UUID.randomUUID().toString(true))
                )
                .map(tenantBind -> {
                r2dbcEntityTemplate.insert(Dc3TenantBind.class)
                    .into("dc3_tenant_bind")
                    .using(tenantBind).subscribe();
            return R.ok("绑定成功");
        }).defaultIfEmpty(R.fail("绑定失败"));
    }
}
