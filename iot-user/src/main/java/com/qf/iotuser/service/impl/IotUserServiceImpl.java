package com.qf.iotuser.service.impl;

import com.dc3.common.bean.R;
import com.qf.iotuser.pojo.Dc3User;
import com.qf.iotuser.service.IotUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
                && StringUtils.hasText(dc3User.getDescription())
                && StringUtils.hasText(dc3User.getPhone())
                && StringUtils.hasText(dc3User.getEmail()))
                .map(dc3User -> {
                    dc3User.setPassword(bCryptPasswordEncoder.encode(dc3User.getPassword()));
                    return dc3User;
                }).flatMap(dc3User ->
                        r2dbcEntityTemplate.insert(Dc3User.class)
                            .into("dc3_user")
                            .using(dc3User)
                                .map(dc3User1 -> {
                                return dc3User1==null ? R.fail("数据错误"):R.ok("添加成功");
                                })
                ).defaultIfEmpty(R.fail("数据错误"));
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
    public Mono<Void> updateUser(Mono<Dc3User> userMono) {
//        userMono.filter(user->StringUtils.hasText(user.getName()))
        return null;
    }
}
