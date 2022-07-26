package com.qf.iotsdk.service.impl;

import com.dc3.common.bean.driver.DriverRegister;
import com.dc3.common.constant.CommonConstant;
import com.dc3.common.model.Driver;
import com.dc3.common.model.DriverEvent;
import com.dc3.common.utils.Dc3Util;
import com.qf.iotsdk.bean.driver.DriverProperty;
import com.qf.iotsdk.service.DriverMetadataService;
import com.qf.iotsdk.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotsdk.service.impl
 * @Description:
 * @Date 2022/7/26 17:37
 */
@Service
public class DriverMetadataServiceImpl implements DriverMetadataService {

    @Value("${spring.application.name}")
    private String serviceName;

    @Value("${server.port}")
    private int port;
    private DriverProperty driverProperty;

    @Autowired
    public void setDriverProperty(DriverProperty driverProperty) {
        this.driverProperty = driverProperty;
    }
    private DriverService driverService;

    @Autowired
    public void setDriverService(DriverService driverService) {
        this.driverService = driverService;
    }

    @Override
    public void init() {
        System.err.println(driverProperty);
        // 驱动对象
        Driver driver = new Driver(
                driverProperty.getName(),
                serviceName, Dc3Util.localHost()
                ,port,driverProperty.getType()
                );
        driverService.driverEventSender(
                new DriverEvent(serviceName,
                        CommonConstant.Driver.Event.DRIVER_REGISTER,
                        new DriverRegister(
                                driverProperty.getTenant(),
                                driver,
                                driverProperty.getDriverAttribute(),
                                driverProperty.getPointAttribute()
                        )
                        )
        );
        System.err.println(driver);
    }
}
