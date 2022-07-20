package com.qf.iotgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import reactivefeign.spring.config.EnableReactiveFeignClients;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotgateway
 * @Description:
 * @Date 2022/7/19 17:37
 */
@SpringBootApplication
@EnableReactiveFeignClients(value = "com.qf")
@EnableFeignClients(value = "com.qf")
public class GateWayStartApp {
    public static void main(String[] args){
        SpringApplication.run(GateWayStartApp.class,args);
    }
}
