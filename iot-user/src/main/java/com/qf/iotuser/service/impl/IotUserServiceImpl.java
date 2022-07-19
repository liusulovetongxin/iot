package com.qf.iotuser.service.impl;

import cn.hutool.core.lang.UUID;
import com.dc3.common.bean.R;
import com.dc3.common.constant.CacheConstant;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qf.feign.CacheFeign;
import com.qf.feign.TenantFeign;
import com.qf.iotuser.pojo.Dc3Tenant;
import com.qf.iotuser.pojo.Dc3TenantBind;
import com.qf.iotuser.pojo.Dc3User;
import com.qf.iotuser.service.IotUserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.data.relational.core.query.Update;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotuser.service.impl
 * @Description:
 * @Date 2022/7/14 20:50
 */
@Service
@Transactional
public class IotUserServiceImpl implements IotUserService {
    private CacheFeign cacheFeign;

    @Autowired
    public void setCacheFeign(CacheFeign cacheFeign) {
        this.cacheFeign = cacheFeign;
    }

    private ApplicationContext context;

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    private ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    private TenantFeign tenantFeign;
    @Autowired
    public void setTenantFeign(TenantFeign tenantFeign) {
        this.tenantFeign = tenantFeign;
    }

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    private R2dbcEntityTemplate r2dbcEntityTemplate;

    @Autowired
    public void setR2dbcEntityTemplate(R2dbcEntityTemplate r2dbcEntityTemplate) {
        this.r2dbcEntityTemplate = r2dbcEntityTemplate;
    }

    @Override
    public Mono<R<Object>> addUser(Mono<Dc3User> userMono) {
       return userMono.filter(dc3User -> StringUtils.hasText(dc3User.getName())
                && StringUtils.hasText(dc3User.getPassword())
                || StringUtils.hasText(dc3User.getDescription())
                || StringUtils.hasText(dc3User.getPhone())
                || StringUtils.hasText(dc3User.getEmail()))
                .map(dc3User -> {
                    dc3User.setId(UUID.randomUUID().toString(true));
                    dc3User.setPassword(bCryptPasswordEncoder.encode(dc3User.getPassword()));
                    return dc3User;
                }).flatMap(dc3User ->
                        r2dbcEntityTemplate.insert(Dc3User.class)
                            .into("dc3_user")
                            .using(dc3User)
                                .map(dc3User1 -> {
                                return dc3User1==null ? R.fail("插入数据库错误"):R.ok("添加成功");
                                })
                ).defaultIfEmpty(R.fail("数据传递不完整"));
    }
    @Override
    public Mono<R<Object>> addUser(String tenantId,Mono<Dc3User> userMono) {
        return userMono.filter(dc3User -> StringUtils.hasText(dc3User.getName())
                // 首先判断是否传递了必要的数据
                        && StringUtils.hasText(dc3User.getPassword())
                        || StringUtils.hasText(dc3User.getDescription())
                        || StringUtils.hasText(dc3User.getPhone())
                        || StringUtils.hasText(dc3User.getEmail()))
                .flatMap(dc3User -> {
                    // 这里判断是否租户下面已经有这个用户的数据了
                    // 本来的思路是通过租户id查到租户下面的所有的用户id，然后取出名字来判断，但是效率可能很低，所以另一个办法
                    // 通过用户名字，找到这个表中所有的用户，如果当前表没有的话，当然是可以直接添加的，如果有了，再查bind表，如果没有关联关系就可一插入
                    return findUserId(dc3User.getName())
                            .flatMap(ids->
                                tenantFeign.findCount(tenantId,ids)
                                        .filter(r -> (Integer) r.getData()==0)
                                        .map(r->dc3User)
                            );
                })
                .map(dc3User -> {
                    //
                    dc3User.setId(UUID.randomUUID().toString(true));
                    dc3User.setPassword(bCryptPasswordEncoder.encode(dc3User.getPassword()));
                    return dc3User;
                }).flatMap(dc3User ->
                        r2dbcEntityTemplate.insert(Dc3User.class)
                                .into("dc3_user")
                                .using(dc3User)
                )
                .map(dc3User ->
                        {
                            HashMap hashMap = new HashMap();
                            hashMap.put("tenantId", tenantId);
                            hashMap.put("userId", dc3User.getId());
                            hashMap.put("description", "普通用户");
                            tenantFeign.bindTenant2User(hashMap).subscribe();
                            return dc3User;
                        }
                        )
                .map(dc3User->R.ok("添加成功"))
                        .defaultIfEmpty(R.fail("数据传递不完整"));
    }


    @Override
    public Mono<Dc3User> findById(String id) {
//        Mono<ArrayList<Dc3User>> mono = r2dbcEntityTemplate.selectOne(Query.query(Criteria.where("id").is(id)), Dc3User.class)
//                .map(dc3User -> {
//                    ArrayList<Dc3User> dc3Users = new ArrayList<>();
//                    dc3Users.add(dc3User);
//                    return dc3Users;
//                });
//        return mono;
        return r2dbcEntityTemplate.selectOne(Query.query(Criteria.where("id").is(id)), Dc3User.class);
    }

    @Override
    public Flux<Dc3User> findByIdIn(List<String> ids) {
        return r2dbcEntityTemplate.select(Dc3User.class).matching(Query.query(Criteria.where("id").in(ids))).all();
    }

    @Override
    public Mono<R<Object>> updateUser(Mono<Dc3User> userMono) {
        return userMono.filter(user->
                StringUtils.hasText(user.getId())
                && StringUtils.hasText(user.getName())
                || StringUtils.hasText(user.getEmail())
                || StringUtils.hasText(user.getDescription())
                || StringUtils.hasText(user.getPhone())
        ).flatMap(dc3User -> {
            HashMap<SqlIdentifier, Object> hashMap = new HashMap<>();
            if (StringUtils.hasText(dc3User.getName())) {
                hashMap.put(SqlIdentifier.quoted("name"), dc3User.getName());
            }
            if (StringUtils.hasText(dc3User.getEmail())) {
                hashMap.put(SqlIdentifier.quoted("email"), dc3User.getEmail());
            }
            if (StringUtils.hasText(dc3User.getPhone())) {
                hashMap.put(SqlIdentifier.quoted("phone"), dc3User.getPhone());
            }
            if (StringUtils.hasText(dc3User.getDescription())) {
                hashMap.put(SqlIdentifier.quoted("description"), dc3User.getDescription());
            }
            return r2dbcEntityTemplate.update(Dc3User.class)
                    .inTable("dc3_user")
                    .matching(Query.query(Criteria.where("id").is(dc3User.getId())))
                    .apply(Update.from(hashMap))
                    .map(integer -> integer==0? R.fail("更新信息失败"): R.ok("更新信息成功"));
        }).defaultIfEmpty(R.fail("数据传递不完整"));
    }

    @Override
    public Mono<R<Object>> deleteById(String userId) {
        return Mono.just(userId).filter(id->StringUtils.hasText(id))
                .flatMap(id->
                        findById(id).flatMap(dc3User ->
                                r2dbcEntityTemplate.update(Dc3User.class)
                                        .inTable("dc3_user")
                                        .matching(Query.query(Criteria.where("id").is(dc3User.getId())))
                                        .apply(Update.update("enable",0))
                                        .map(integer -> integer==0? R.fail("更新失败"):R.ok("更新成功"))
                                )
                        ).defaultIfEmpty(R.fail("用户不存在"));
    }

    @Override
    public Mono<Dc3User> findUserAndTent(Mono<Dc3User> userMono) {
        return userMono.filter(user -> StringUtils.hasText(user.getId()))
                .flatMap(user ->
                        findById(user.getId()).flatMap(dc3User ->
                                r2dbcEntityTemplate
                                        .selectOne(Query.query(Criteria.where("user_id").is(dc3User.getId())), Dc3TenantBind.class)
                                        .flatMap(dc3TenantBind -> tenantFeign.findById(dc3TenantBind.getTenantId())
                                                        .map(r->{user.setDc3Tenant(
                                                                objectMapper.convertValue(r.getData(), Dc3Tenant.class)
                                                        );return user;})
                                        )
                        )
                );
    }

    @Override
    public Mono<R<Object>> loginByTenant(String tenantId, Mono<Dc3User> userMono) {
        return userMono.filter(dc3User -> StringUtils.hasText(dc3User.getName()) && StringUtils.hasText(dc3User.getPassword()) && StringUtils.hasText(tenantId))
                .flatMap(dc3User -> tenantFeign.findByIdIn(tenantId)
                        .map(r -> (objectMapper.convertValue(r.getData(),Dc3Tenant.class)).getUsers())
                        .flatMapIterable(dc3Users -> dc3Users)
                        .filter(dc3User1 -> dc3User1.getName().equals(dc3User.getName()))
                        .collectList()
                        .map(list -> {
                            return bCryptPasswordEncoder.matches(dc3User.getPassword(),objectMapper.convertValue(list.get(0),Dc3User.class).getPassword()) ? R.ok("登录成功") : R.fail("登录失败");
                        })
                ).defaultIfEmpty(R.fail("用户名或密码错误"));
    }

    @Override
    public Flux<Dc3User> findByName(String name) {
        return r2dbcEntityTemplate.select(Dc3User.class).from("dc3_user")
                .matching(Query.query(Criteria.where("name").is(name))).all();
    }

    @Override
    public Mono<List<String>> findUserId(String name) {
        return findByName(name).map(Dc3User::getId).collectList();
    }


    @Override
    public Mono<String> login(String userName, String password, String tenantName) {
        return findByUserNameAndTenantName(userName, tenantName)
                .filter(dc3User -> bCryptPasswordEncoder.matches(password,dc3User.getPassword()))
                .map(dc3User -> {
                    String salt = UUID.randomUUID().toString(true);
                    String key = "zck"+CacheConstant.Entity.TENANT+tenantName+CacheConstant.Entity.USER+userName+CacheConstant.Suffix.SALT;
                    cacheFeign.set(key,salt).subscribe();
                    Instant instant = Instant.now();
                    Instant seconds = instant.plusSeconds(2 * 60);
                    String jwt = Jwts.builder()
                            .setSubject(userName)
                            .claim("tenantName", tenantName)
                            .claim("phone", dc3User.getPhone())
                            .claim("email", dc3User.getEmail())
                            .claim("description", dc3User.getDescription())
                            .setExpiration(Date.from(seconds)) // 设置过期时间
//                            .setNotBefore(Date.from(seconds)) 设置什么时间之前不可用
                            .setIssuedAt(Date.from(instant))// 设置什么时候开始
                            .signWith(SignatureAlgorithm.HS256, salt.getBytes(StandardCharsets.UTF_8))
                            .compact();
                    return jwt;
                });
    }
    @Override
    public Mono<Dc3User> findByUserNameAndTenantName(String username, String tenantName) {
        return r2dbcEntityTemplate.selectOne(Query.query(Criteria.where("name").is(username).and(Criteria.where("tenant_name").is(tenantName))), Dc3User.class);
    }

}
