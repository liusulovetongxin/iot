package com.qf.iotgateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotgateway.config
 * @Description:
 * @Date 2022/7/19 17:38
 */
@Configuration
public class GateWayConfig  {
    @Bean
    public KeyResolver keyResolver(){
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostString());
    }
    @Bean
    public RedisRateLimiter redisRateLimiter(){
        return new RedisRateLimiter(1,1,1);
    }
    @Bean
    public RouteLocator route(RouteLocatorBuilder builder, KeyResolver keyResolver, RedisRateLimiter redisRateLimiter){
        return builder.routes()
                .route("tokenroute", predicateSpec ->
                        predicateSpec.path("/api/v1/user/**")
                                .filters(gatewayFilterSpec ->
                                        gatewayFilterSpec.stripPrefix(2)
                                                .requestRateLimiter(config ->
                                                        config.setKeyResolver(keyResolver)
                                                                .setRateLimiter(redisRateLimiter))).uri("lb://zck-iot-user")
                ).route(predicateSpec ->
                        predicateSpec.path("/api/v1/tenant/**")
                                .filters(gatewayFilterSpec ->
                                        gatewayFilterSpec.stripPrefix(2)
                                                .requestRateLimiter(config ->
                                                        config.setKeyResolver(keyResolver)
                                                                .setRateLimiter(redisRateLimiter)
                                                )
                                ).uri("lb://zck-iot-tenant")
                ).build();
    }
}
