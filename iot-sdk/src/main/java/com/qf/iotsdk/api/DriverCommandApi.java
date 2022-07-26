package com.qf.iotsdk.api;

import com.dc3.common.bean.R;
import com.dc3.common.bean.driver.command.CmdParameter;
import com.dc3.common.bean.point.PointValue;
import com.dc3.common.constant.ServiceConstant;
import com.dc3.common.valid.ValidatableList;
import com.qf.iotsdk.service.DriverCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotsdk.api
 * @Description:
 * @Date 2022/7/25 16:18
 */
@RestController
@RequestMapping(ServiceConstant.Driver.COMMAND_URL_PREFIX)
public class DriverCommandApi  {

    private DriverCommandService driverCommandService;

    @Autowired
    public void setDriverCommandService(DriverCommandService driverCommandService) {
        this.driverCommandService = driverCommandService;
    }

    @PostMapping("/read")
    public R<List<PointValue>> readPoint(ValidatableList<CmdParameter> cmdParameters) {
    return R.ok(         cmdParameters.stream().map(cmdParameter ->
                driverCommandService.read(cmdParameter.getDeviceId(), cmdParameter.getPointId())
        ).collect(Collectors.toList()));
    }

    @PostMapping("/write")
    public R<Boolean> writePoint(ValidatableList<CmdParameter> cmdParameters) {
        try {
            cmdParameters.forEach(cmdParameter ->
                    driverCommandService.write(cmdParameter.getDeviceId(),cmdParameter.getPointId(),cmdParameter.getValue())
                    );
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail(e.getMessage());
        }
        return R.ok();
    }
}
