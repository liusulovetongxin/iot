package com.qf.iotsdk.service;

import com.dc3.common.bean.point.PointValue;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotsdk.service
 * @Description:
 * @Date 2022/7/25 16:23
 */

public interface DriverCommandService {
    PointValue read(String deviceId, String pointId);
    Boolean write(String deviceId, String pointId, String value);
}
