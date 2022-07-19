package com.qf.iotuser.service;

import reactor.core.publisher.Mono;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotuser.service
 * @Description:
 * @Date 2022/7/19 17:56
 */

public interface TokenService {
    Mono<Integer> CheckToken(String token);
}
