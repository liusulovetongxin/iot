package com.qf.iotmqtt.service.impl;

import com.dc3.common.bean.driver.AttributeInfo;
import com.dc3.common.model.Device;
import com.dc3.common.model.Point;
import com.qf.iotsdk.service.DriverCustomService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotmqtt.service.impl
 * @Description:
 * @Date 2022/7/26 19:40
 */
@Service
public class DriverCustomServiceImpl implements DriverCustomService {
    @Override
    public void initial() {

    }

    @Override
    public String read(Map<String, AttributeInfo> driverInfo, Map<String, AttributeInfo> pointInfo, Device device, Point point) throws Exception {
        return null;
    }

    @Override
    public Boolean write(Map<String, AttributeInfo> driverInfo, Map<String, AttributeInfo> pointInfo, Device device, AttributeInfo value) throws Exception {
        return null;
    }

    @Override
    public void schedule() {

    }
}
