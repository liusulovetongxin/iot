package com.qf.iotblackip;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotblackip
 * @Description:
 * @Date 2022/7/20 16:28
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages ={ "com.qf.iotblackip.mapper"})
public class BlackIpStartApp {
    public static void main(String[] args){
        SpringApplication.run(BlackIpStartApp.class,args);
    }
}
