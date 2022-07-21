package com.qf.iotblackip.controller;

import com.dc3.common.bean.R;
import com.qf.iotblackip.dto.BlackIpDto;
import com.qf.iotblackip.pojo.Dc3BlackIp;
import com.qf.iotblackip.service.BlackIpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotblackip.controller
 * @Description:
 * @Date 2022/7/20 16:38
 */
@RestController
@RequestMapping("/blackip")
public class BlackIpController {
    private BlackIpService blackIpService;

    @Autowired
    public void setBlackIpService(BlackIpService blackIpService) {
        this.blackIpService = blackIpService;
    }

    @PostMapping
    public R addBlackIp(@RequestBody BlackIpDto blackIpDto){
        Dc3BlackIp dc3BlackIp = new Dc3BlackIp();
        dc3BlackIp.setIp(blackIpDto.getIp());
        dc3BlackIp.setDescription(blackIpDto.getDescription());
        blackIpService.addBlackIp(dc3BlackIp);
        return R.ok();
    }
    @GetMapping("/list/simple")
    public R findAllIp2Dto(){
        return R.ok(blackIpService.findAllIp2Dto());
    }

    @GetMapping("/refresh")
    public void refresh(){
        blackIpService.refresh();
    }

    @PostMapping("/delete/{id}")
    public void deleteId(@PathVariable String id ){
        blackIpService.deleteId(id);
    }
}
