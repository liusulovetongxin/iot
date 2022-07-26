package com.qf.iotsdk.service.impl;

import com.dc3.common.constant.ValueConstant;
import com.dc3.common.model.DriverEvent;
import com.dc3.common.model.Point;
import com.qf.iotsdk.bean.driver.DriverContext;
import com.qf.iotsdk.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotsdk.service.impl
 * @Description:
 * @Date 2022/7/26 11:32
 */
@Service
public class DriverServiceImpl implements DriverService {
    private DriverContext driverContext;

    @Autowired
    public void setDriverContext(DriverContext driverContext) {
        this.driverContext = driverContext;
    }

    @Override
    public String convertValue(String deviceId, String pointId, String rawString) {
        Point point = driverContext.getPointByDeviceIdAndPointId(deviceId, pointId);
        switch (point.getType()){
            case ValueConstant.Type.STRING:
                return rawString;
            case ValueConstant.Type.INT:
            case ValueConstant.Type.BYTE:
            case ValueConstant.Type.SHORT:
            case ValueConstant.Type.DOUBLE:
            case ValueConstant.Type.LONG:
            case ValueConstant.Type.FLOAT:
                Double temp = Double.parseDouble(rawString);
                if (point.getMinimum() != null && temp < point.getMinimum()) {
                    System.err.println("当前传递的数据小于最小值");
                    //结束操作
                }

                if (point.getMaximum() != null && temp > point.getMaximum()) {
                    System.err.println("当前传递的数据超过最大值");
                    //结束操作
                }
                if (StringUtils.hasText(point.getFormat())) {
                    return String.format(point.getFormat(), temp);
                }else{
                    return String.valueOf(temp);
                }
        }
        return null;
    }

    @Override
    public void driverEventSender(DriverEvent driverEvent) {

    }
}
