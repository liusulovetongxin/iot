package com.qf.iotgateway.filters;

import com.qf.feign.CacheFeign;
import com.qf.iotgateway.cache.ForbiddenPathCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotgateway.filters
 * @Description:
 * @Date 2022/7/21 15:17
 */

@Component
public class FrbiddenPathGlobalFilter implements GlobalFilter, Ordered {
    private ForbiddenPathCache forbiddenPathCache;

    @Autowired
    public void setForbiddenPathCache(ForbiddenPathCache forbiddenPathCache) {
        this.forbiddenPathCache = forbiddenPathCache;
    }

    private CacheFeign cacheFeign;

    @Autowired
    public void setCacheFeign(CacheFeign cacheFeign) {
        this.cacheFeign = cacheFeign;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        return cacheFeign.sMember(CacheConstant.Entity.BLACK_IP)
//                .filter(r-> !((Collection) r.getData()).contains(exchange.getRequest().getRemoteAddress().getHostString()))
//                .map(r-> chain.filter(exchange))
//                .defaultIfEmpty(
//                        exchange.getResponse().writeAndFlushWith(Mono.just(Mono.just(
//                                DefaultDataBufferFactory.sharedInstance.allocateBuffer()
//                                        .write("当前ip不可访问".getBytes(StandardCharsets.UTF_8))
//                        )))
//                )
//                .flatMap(result->result);
        return Mono.just(forbiddenPathCache.getPathCacheSet())
                .filter(collection-> !collection.contains(exchange.getRequest().getURI().getPath()))
                .map(r-> chain.filter(exchange))
                .defaultIfEmpty(
                        exchange.getResponse().writeAndFlushWith(Mono.just(Mono.just(
                                DefaultDataBufferFactory.sharedInstance.allocateBuffer()
                                        .write("网络故障，请检查网络".getBytes(StandardCharsets.UTF_8))
                        )))
                )
                .flatMap(result->result);

    }

    @Override
    public int getOrder() {
        return 1;
    }
}
