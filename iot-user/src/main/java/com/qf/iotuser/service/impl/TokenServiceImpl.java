package com.qf.iotuser.service.impl;

import com.dc3.common.bean.R;
import com.dc3.common.constant.CacheConstant;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qf.feign.CacheFeign;
import com.qf.iotuser.service.TokenService;
import io.jsonwebtoken.Jwts;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotuser.service.impl
 * @Description:
 * @Date 2022/7/19 17:57
 */
@Service
public class TokenServiceImpl implements TokenService {
    private CacheFeign cacheFeign;

    @Autowired
    public void setCacheFeign(CacheFeign cacheFeign) {
        this.cacheFeign = cacheFeign;
    }

    private ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Integer> CheckToken(String token) {
     return Mono.just(token)
                .map(jwt->jwt.split("\\."))
                .map(strings -> strings[1])
                .map(s -> Base64.decodeBase64(s))
                .map(json-> {
                    try {
                        return objectMapper.readValue(json, Map.class);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .flatMap(map -> {
                    String userName = (String) map.get("sub");
                    String tenantName = (String) map.get("tenantName");
                    String key = "zck"+ CacheConstant.Entity.TENANT+tenantName+CacheConstant.Entity.USER+userName+CacheConstant.Suffix.SALT;
                    return cacheFeign.get(key).map(R::getData);
                })
                .map(salt->{
                    try {
                        Jwts.parser().setSigningKey(salt.toString().getBytes(StandardCharsets.UTF_8)).parse(token);
                        return 1;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return 0;
                    }
                })
             .onErrorReturn(0)
             .defaultIfEmpty(0);
    }

    @Override
    public Mono<Integer> CheckTokenAll(String token) {
        return Mono.just(token)
                .map(jwt->jwt.split("\\."))
                .map(strings -> strings[1])
                .map(s -> Base64.decodeBase64(s))
                .map(json-> {
                    try {
                        return objectMapper.readValue(json, Map.class);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .flatMap(map -> {
                    String userName = (String) map.get("sub");
                    String tenantName = (String) map.get("tenantName");
                    String key = "zck"+ CacheConstant.Entity.TENANT+tenantName+CacheConstant.Entity.USER+userName+CacheConstant.Suffix.SALT;
                    return cacheFeign.get(key).map(R::getData);
                })
                .map(redisToken->
                     token.equals(redisToken) ? 1:0
                )
                .onErrorReturn(0)
                .defaultIfEmpty(0);
    }
}
