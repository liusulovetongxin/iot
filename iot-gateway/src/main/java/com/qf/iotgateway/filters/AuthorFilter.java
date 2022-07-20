package com.qf.iotgateway.filters;

import com.dc3.common.constant.ServiceConstant;
import com.qf.feign.UserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
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
 * @Date 2022/7/20 11:06
 */
@Component
public class AuthorFilter implements GatewayFilter, Ordered {
    private UserFeign userFeign;

    @Autowired
    public void setUserFeign(UserFeign userFeign) {
        this.userFeign = userFeign;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst(ServiceConstant.Header.X_AUTH_TOKEN);
return
        Mono.just(token==null?"":token)
                .flatMap(s -> userFeign.checkToken(s))
                .filter(r -> (Integer)r.getData()!=0)
                .map(r -> chain.filter(exchange))
                .defaultIfEmpty(exchange.getResponse().writeAndFlushWith(
                  Mono.just( Mono.just(DefaultDataBufferFactory.sharedInstance.allocateBuffer().write("没有登录，请登录后再次尝试".getBytes(StandardCharsets.UTF_8))))
                ))
                .flatMap(voidMono -> voidMono);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
