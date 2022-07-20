package com.qf.iotgateway.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotgateway.filter
 * @Description:
 * @Date 2022/7/19 17:49
 */
@Component
public class AuthorGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthorGatewayFilterFactory.Config> {

    private AuthorFilter authorFilter;

    @Autowired
    public void setAuthorFilter(AuthorFilter authorFilter) {
        this.authorFilter = authorFilter;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return authorFilter;
    }

    public static class Config{

    }



}
