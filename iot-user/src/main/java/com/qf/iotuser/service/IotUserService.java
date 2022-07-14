package com.qf.iotuser.service;

import com.dc3.common.bean.R;
import com.qf.iotuser.pojo.Dc3User;
import reactor.core.publisher.Mono;

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

    Mono<R> findById(String id);
}
