package com.qf.iotsdk.service.impl;

import com.dc3.common.bean.point.PointValue;
import com.dc3.common.model.Device;
import com.qf.iotsdk.bean.driver.DriverContext;
import com.qf.iotsdk.service.DriverCommandService;
import com.qf.iotsdk.service.DriverCustomService;
import com.qf.iotsdk.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotsdk.service.impl
 * @Description:
 * @Date 2022/7/25 16:23
 */
@Service
public class DriverCommandServiceImpl implements DriverCommandService {
    private DriverContext driverContext;

    @Autowired
    public void setDriverContext(DriverContext driverContext) {
        this.driverContext = driverContext;
    }

    private DriverCustomService driverCustomService;

    @Autowired
    public void setDriverCustomService(DriverCustomService driverCustomService) {
        this.driverCustomService = driverCustomService;
    }
    private DriverService driverService;

    @Autowired
    public void setDriverService(DriverService driverService) {
        this.driverService = driverService;
    }

    @Override
    public PointValue read(String deviceId, String pointId) {
        // 先根据设备的id获取到设备的信息
        Device device = driverContext.getDeviceByDeviceId(deviceId);
        // 具体怎么读取应该是驱动的事情
        // 所以这个操作应该是要交给具体的驱动
        try{

            String rawString = driverCustomService.read(driverContext.getDriverInfoByDeviceId(deviceId), driverContext.getPointInfoByDeviceIdAndPointId(deviceId, pointId),
                    device, driverContext.getPointByDeviceIdAndPointId(deviceId, pointId));
            // 创建一个位号的数据
            PointValue pointValue = new PointValue(deviceId , pointId, rawString, driverService.convertValue(deviceId, pointId, rawString));
            return pointValue;

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean write(String deviceId, String pointId, String value) {
        return false;
    }
}
