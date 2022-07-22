package com.qf.iotforbiddenpath.controller;

import com.dc3.common.bean.R;
import com.qf.iotforbiddenpath.dto.Dc3ForbiddenPathDto;
import com.qf.iotforbiddenpath.service.ForbiddenPathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotforbiddenpath.controller
 * @Description:
 * @Date 2022/7/22 10:12
 */
@RestController
@RequestMapping("/forbiddenpath")
public class IotForbiddenPathController {
    private ForbiddenPathService forbiddenPathService;

    @Autowired
    public void setForbiddenPathService(ForbiddenPathService forbiddenPathService) {
        this.forbiddenPathService = forbiddenPathService;
    }

    @PostMapping
    public R addPath(@RequestBody Dc3ForbiddenPathDto dc3ForbiddenPathDto) {
        forbiddenPathService.addPath(dc3ForbiddenPathDto);
        return R.ok();
    }
    @GetMapping("/path/{id}")
    public R findById(@PathVariable String id){
        return R.ok(forbiddenPathService.findById(id));
    }
    @GetMapping("/list")
    public R findList(){
        return R.ok(forbiddenPathService.findList());
    }
    @PostMapping("/delete/{id}")
    public R deleteById(@PathVariable String id){
        forbiddenPathService.deleteById(id);
        return R.ok();
    }
    @GetMapping("/refresh")
    public R refresh(){
        forbiddenPathService.refresh();
        return R.ok();
    }
}
