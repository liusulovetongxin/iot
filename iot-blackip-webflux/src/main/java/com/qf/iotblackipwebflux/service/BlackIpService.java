package com.qf.iotblackipwebflux.service;

import com.qf.iotblackipwebflux.dto.BlackIpDto;
import com.qf.iotblackipwebflux.pojo.Dc3BlackIp;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotblackipwebflux.service
 * @Description:
 * @Date 2022/7/22 15:21
 */

public interface BlackIpService {
    Mono<Dc3BlackIp> addBlackIp(Mono<BlackIpDto> blackIpDtoMono);

    Mono<List<String>> findList();

    Mono<Integer> updateById(String id);
}
