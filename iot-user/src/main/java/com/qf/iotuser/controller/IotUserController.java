package com.qf.iotuser.controller;

import com.dc3.common.bean.R;
import com.qf.iotuser.pojo.Dc3User;
import com.qf.iotuser.service.IotUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotuser.controller
 * @Description:
 * @Date 2022/7/14 21:26
 */
@RestController
@RequestMapping("/user")
public class IotUserController {
    private IotUserService userService;

    @Autowired
    public void setUserService(IotUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public Mono<R<Object>> addUser(@RequestBody Mono<Dc3User> userMono){
        return userService.addUser(userMono);
    }
    @GetMapping("/info/{id}")
    public Mono<R> findById(@PathVariable String id){
        return userService.findById(id).map(dc3User -> R.ok(dc3User));
    }

//    @GetMapping("/info/{id}")
//    public Mono<ArrayList<Dc3User>> findById(@PathVariable String id){
//        return userService.findById(id);
//    }

    @PostMapping("/list")
    public Mono<R> findByIdIn(@RequestBody List<String> ids){
        return userService.findByIdIn(ids).collectList().map(list->R.ok(list));
    }
}
