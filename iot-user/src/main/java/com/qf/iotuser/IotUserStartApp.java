package com.qf.iotuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import reactivefeign.spring.config.EnableReactiveFeignClients;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotuser
 * @Description:
 * @Date 2022/7/14 20:43
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableReactiveFeignClients(basePackages = "com.qf")
public class IotUserStartApp {
    public static void main(String[] args){
        SpringApplication.run(IotUserStartApp.class,args);
    }
}
