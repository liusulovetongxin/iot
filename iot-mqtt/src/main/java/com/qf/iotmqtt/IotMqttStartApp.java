package com.qf.iotmqtt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotmqtt
 * @Description:
 * @Date 2022/7/26 19:38
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.qf")
public class IotMqttStartApp {
    public static void main(String[] args){
        SpringApplication.run(IotMqttStartApp.class,args);
    }
}
