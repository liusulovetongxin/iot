package com.qf.iotuser.service.impl;

import cn.hutool.core.lang.UUID;
import com.dc3.common.bean.R;
import com.qf.iotuser.pojo.Dc3User;
import com.qf.iotuser.service.IotUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

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
    private R2dbcEntityTemplate r2dbcEntityTemplate;

    @Autowired
    public void setR2dbcEntityTemplate(R2dbcEntityTemplate r2dbcEntityTemplate) {
        this.r2dbcEntityTemplate = r2dbcEntityTemplate;
    }

    @Override
    public Mono<R<Object>> addUser(Mono<Dc3User> userMono) {
return userMono.filter(
                user-> StringUtils.hasText(user.getName())&&StringUtils.hasText(user.getPassword())
        )
        .doOnNext(user->user.setId(UUID.randomUUID().toString(true)))
        .map(user->{
                     r2dbcEntityTemplate.insert(Dc3User.class).into("dc3_user")
                            .using(user).subscribe();
                     return R.ok("注册成功");}
        ).defaultIfEmpty(R.fail("注册错误，数据不完整"));
    }

    @Override
    public Mono<R> findById(String id) {
        return r2dbcEntityTemplate.selectOne(Query.query(Criteria.where("id").is(id)), Dc3User.class)
                .map(user->R.ok(user));
    }
}
