package com.qf.iottenant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import reactivefeign.spring.config.EnableReactiveFeignClients;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iottenant
 * @Description: 主程序
 * @Date 2022/7/14 11:30
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableReactiveFeignClients(basePackages = "com.qf")
public class IotTenantStartApp {
    public static void main(String[] args){
        SpringApplication.run(IotTenantStartApp.class,args);
    }
}
