package com.qf.iotsdk.init;


import com.qf.iotsdk.service.DriverMetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotsdk.init
 * @Description:
 * @Date 2022/7/26 17:36
 */
@Component
public class DriverInitRunner implements ApplicationRunner {

    public DriverMetadataService driverMetadataService;

    @Autowired
    public void setDriverMetadataService(DriverMetadataService driverMetadataService) {
        this.driverMetadataService = driverMetadataService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        driverMetadataService.init();
    }
}
