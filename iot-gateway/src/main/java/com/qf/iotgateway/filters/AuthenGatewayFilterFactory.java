package com.qf.iotgateway.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotgateway.filter
 * @Description:
 * @Date 2022/7/19 17:49
 */

public class AuthenGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthenGatewayFilterFactory.Config> {
    private AuthenFilter authenFilter;

    @Autowired
    public void setAuthenFilter(AuthenFilter authenFilter) {
        this.authenFilter = authenFilter;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return authenFilter;
    }

    public static class Config{

    }

    static class AuthenFilter implements GatewayFilter, Ordered{

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            return null;
        }

        @Override
        public int getOrder() {
            return 0;
        }
    }

}
