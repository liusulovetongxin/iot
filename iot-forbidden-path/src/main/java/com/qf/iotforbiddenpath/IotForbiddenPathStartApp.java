package com.qf.iotforbiddenpath;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotforbiddenpath
 * @Description:
 * @Date 2022/7/22 10:11
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = {"com.qf.iotforbiddenpath.mapper"})
@EnableFeignClients(basePackages = "com.qf")
public class IotForbiddenPathStartApp {
    public static void main(String[] args){
        SpringApplication.run(IotForbiddenPathStartApp.class,args);
    }
}
