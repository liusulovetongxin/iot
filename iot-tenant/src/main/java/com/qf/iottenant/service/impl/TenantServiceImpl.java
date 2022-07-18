package com.qf.iottenant.service.impl;

import cn.hutool.core.lang.UUID;
import com.dc3.common.bean.R;
import com.qf.feign.UserFeign;
import com.qf.iottenant.pojo.Dc3Tenant;
import com.qf.iottenant.pojo.Dc3TenantBind;
import com.qf.iottenant.pojo.Dc3User;
import com.qf.iottenant.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.data.relational.core.query.Update;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
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
    private UserFeign userFeign;

    @Autowired
    public void setUserFeign(UserFeign userFeign) {
        this.userFeign = userFeign;
    }

    private R2dbcEntityTemplate r2dbcEntityTemplate;

    @Autowired
    public void setR2dbcEntityTemplate(R2dbcEntityTemplate r2dbcEntityTemplate) {
        this.r2dbcEntityTemplate = r2dbcEntityTemplate;
    }
    @Override
    public Mono<R<Object>> addTenant(Mono<Dc3Tenant> tenantMono) {
        return tenantMono
                .filter(tenant -> StringUtils.hasText(tenant.getName()) )
                .doOnNext(tenant -> {
                    tenant.setId(UUID.randomUUID().toString(true));
                })
                .flatMap(tenant -> r2dbcEntityTemplate.insert(Dc3Tenant.class).into("dc3_tenant").using(tenant).map(result -> R.ok())
                ).defaultIfEmpty(R.fail("数据不能为空"));
    }
    @Override
    public Mono<Dc3Tenant> findById(String id) {
//        List<Dc3User> dc3Users = new ArrayList<>();
        return r2dbcEntityTemplate.selectOne(Query.query(Criteria.where("id").is(id)),Dc3Tenant.class);
//                .flatMap(dc3Tenant -> openfeign
//                        .findById(id).map(new Function<ArrayList<Dc3User>, List<Dc3User>>() {
//                            @Override
//                            public List<Dc3User> apply(ArrayList<Dc3User> dc3Users) {
//                                return dc3Users;
//                            }
//                        }).flatMap(value-> {
//                            System.err.println("方法中的"+dc3Tenant);
////                            System.err.println(value);
//                            dc3Tenant.setUsers(value);
//                            System.err.println("方法修改后的"+dc3Tenant);
//                            return Mono.just(dc3Tenant);
//                        }));
//        dc3TenantMono.subscribe(value-> System.err.println(value));
//        return dc3TenantMono;
    }



    @Override
    public Flux<Dc3Tenant> findByName(String name) {
        return r2dbcEntityTemplate.select(Dc3Tenant.class).matching(Query.query(Criteria.where("name").is(name))).all();
    }

    /**
     * 这个思路不是很完全
     * @param tenantMono
     * @return
     */
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
//                    return mono;
                })
                .defaultIfEmpty(R.fail("数据传输错误"));
    }

    @Override
    public Mono<R<Object>> updateDc3Tenant(Mono<Dc3Tenant> tenantMono) {
        return tenantMono.filter(dc3Tenant -> StringUtils.hasText(dc3Tenant.getId())&&StringUtils.hasText(dc3Tenant.getName())||StringUtils.hasText(dc3Tenant.getDescription()))
                .flatMap(dc3Tenant -> {
                    return findById(dc3Tenant.getId())
                            .map(tenant->{
//                        tenant.setName(dc3Tenant.getName());
//                        tenant.setDescription(dc3Tenant.getDescription());
                        return dc3Tenant;
                    });
                }).flatMap(dc3Tenant -> {
                    HashMap<SqlIdentifier, Object> hashMap = new HashMap<>();
                    if (StringUtils.hasText(dc3Tenant.getName())){
                        hashMap.put(SqlIdentifier.unquoted("name"), dc3Tenant.getName());
                    }
                    if (StringUtils.hasText(dc3Tenant.getDescription())){
                        hashMap.put(SqlIdentifier.unquoted("description"), dc3Tenant.getDescription());
                    }
                    return r2dbcEntityTemplate.update(Dc3Tenant.class)
                            .inTable("dc3_tenant")
                            .matching(Query.query(Criteria.where("id").is(dc3Tenant.getId())))
                            .apply(Update.from(hashMap))
                            .map(integer -> {
                                return integer==0? R.fail("数据不存在"):R.ok("成功");
                            });
                }).defaultIfEmpty(R.fail("数据不存在"));
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

    @Override
    public Mono<R<Object>> bindTenant2User(Dc3TenantBind tenantBindMono) {
        tenantBindMono.setId(UUID.randomUUID().toString(true));
        return r2dbcEntityTemplate.insert(Dc3TenantBind.class)
                .into("dc3_tenant_bind")
                .using(tenantBindMono)
                .map(dc3TenantBind -> R.ok(dc3TenantBind));
    }
    @Override
    public Mono<Dc3Tenant> findByUsers(String tenantId) {
        return findById(tenantId).flatMap(dc3Tenant ->
                r2dbcEntityTemplate
                        .select(Dc3TenantBind.class)
                        .from("dc3_tenant_bind")
                        .matching(Query.query(Criteria.where("tenant_id").is(dc3Tenant.getId())))
                        .all()
                        .map(dc3TenantBind -> dc3TenantBind.getUserId())
                        .collectList()
                        .flatMap(
                                userList-> userFeign.findByIdIn(userList)
                                        .map(r -> {
                                            dc3Tenant.setUsers((List<Dc3User>) r.getData());
                                            return dc3Tenant;
                                        })
                        )

                );
    }

    @Override
    public Mono<Integer> findCount(String tenantId, List<String> ids) {

        return r2dbcEntityTemplate.select(Dc3TenantBind.class)
                .from("dc3_tenant_bind")
                .matching(Query.query(Criteria.where("tenant_id").is(tenantId).and(Criteria.where("user_id").in(ids))
                        )).all()
                .collectList()
                .map(list->list.size());
//        下面有问题，这个count函数可能有大问题，cannot map * 我靠
//        return r2dbcEntityTemplate.count(Query.query(Criteria.where("tenant_id").is(tenantId).and(Criteria.where("user_id").in(ids))), Dc3TenantBind.class);
    }


}
