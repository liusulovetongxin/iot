package com.qf.iotsdk.service;

import com.dc3.common.model.DriverEvent;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotsdk.service
 * @Description:
 * @Date 2022/7/26 11:32
 */

public interface DriverService {
    String convertValue(String deviceId,String pointId,String rawString);

    void driverEventSender(DriverEvent driverEvent);
}
