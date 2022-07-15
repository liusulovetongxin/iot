package com.qf.iotuser.service;

import com.dc3.common.bean.R;
import com.qf.iotuser.pojo.Dc3User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotuser.service
 * @Description:
 * @Date 2022/7/14 20:48
 */

public interface IotUserService {
    Mono<R<Object>> addUser(Mono<Dc3User> userMono);

    Mono<Dc3User> findById(String id);

    Flux<Dc3User> findByIdIn(List<String> ids);

    Mono<Void> updateUser(Mono<Dc3User> userMono);
}
