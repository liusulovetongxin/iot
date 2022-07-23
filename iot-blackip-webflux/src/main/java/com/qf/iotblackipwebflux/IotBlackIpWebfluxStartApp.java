package com.qf.iotblackipwebflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import reactivefeign.spring.config.EnableReactiveFeignClients;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotblackipwebflux
 * @Description:
 * @Date 2022/7/22 15:21
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableReactiveFeignClients(basePackages = "com.qf")
public class IotBlackIpWebfluxStartApp {
    public static void main(String[] args){
        SpringApplication.run(IotBlackIpWebfluxStartApp.class,args);
    }
}
