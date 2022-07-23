package com.qf.iotblackipwebflux.service.impl;

import cn.hutool.core.lang.UUID;
import com.qf.iotblackipwebflux.dto.BlackIpDto;
import com.qf.iotblackipwebflux.events.BlackIpChangeEvent;
import com.qf.iotblackipwebflux.pojo.Dc3BlackIp;
import com.qf.iotblackipwebflux.service.BlackIpService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.data.relational.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotblackipwebflux.service.impl
 * @Description:
 * @Date 2022/7/22 15:24
 */
@Service
@Transactional
public class BlackIpServiceImpl implements BlackIpService {
    private ApplicationContext context;

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    private R2dbcEntityTemplate r2dbcEntityTemplate;

    @Autowired
    public void setR2dbcEntityTemplate(R2dbcEntityTemplate r2dbcEntityTemplate) {
        this.r2dbcEntityTemplate = r2dbcEntityTemplate;
    }

    @Override
    public Mono<Dc3BlackIp> addBlackIp(Mono<BlackIpDto> blackIpDtoMono) {
        return blackIpDtoMono.filter(blackIpDto -> StringUtils.hasText(blackIpDto.getIp()))
                .map(blackIpDto -> {
                    Dc3BlackIp dc3BlackIp = new Dc3BlackIp();
                    BeanUtils.copyProperties(blackIpDto, dc3BlackIp);
                    dc3BlackIp.setId(UUID.randomUUID().toString(true));
                    return dc3BlackIp;
                })
                .flatMap(dc3BlackIp ->
                        r2dbcEntityTemplate.insert(Dc3BlackIp.class)
                                .into("dc3_black_ip")
                                .using(dc3BlackIp)
                        );
    }

    @Override
    public Mono<List<String>> findList() {
        return r2dbcEntityTemplate.select(Dc3BlackIp.class)
                .from("dc3_black_ip")
                .matching(Query.query(
                        Criteria.where("enable")
                                .is(1)
                )).all()
                .map(Dc3BlackIp::getIp)
                .collectList();
    }

    @Override
    public Mono<Integer> updateById(String id) {
        return Mono.just(id)
                .filter(s -> StringUtils.hasText(s))
                .map(s -> {
                    System.err.println("更新的操作");
                     r2dbcEntityTemplate.update(Dc3BlackIp.class)
                            .inTable("dc3_black_ip")
                            .matching(Query.query(Criteria.where("id").is(s))
                            ).apply(
                                    Update.update("enable", 0)
                                            .set("deleted", 1)
                            ).subscribe(shabi-> {
                                 context.publishEvent(new BlackIpChangeEvent());
                             });
                    return 1;}
                );
    }
}
