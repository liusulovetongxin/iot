package com.qf.iotuser.controller;

import com.dc3.common.bean.R;
import com.dc3.common.constant.ServiceConstant;
import com.qf.iotuser.pojo.Dc3User;
import com.qf.iotuser.service.IotUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
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
    public Mono<R<Object>> addUser(@RequestParam("tenantId")String tenantId,@RequestBody Mono<Dc3User> userMono){
        return userService.addUser(tenantId,userMono);
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

    @PostMapping("/update")
    public Mono<R<Object>> updateDc3User(@RequestBody Mono<Dc3User> dc3UserMono){
        return userService.updateUser(dc3UserMono);
    }
    @DeleteMapping("/{id}")
    public Mono<R<Object>> deleteDc3User(@PathVariable String id){
        return userService.deleteById(id);
    }
    @PostMapping ("/tenant")
    public Mono<R<Object>> findUserAndTenant(@RequestBody Mono<Dc3User> userMono){
        return userService.findUserAndTent(userMono).map(result->R.ok(result));
    }

    @PostMapping("/login")
    public Mono<R<Object>> login(String userName, String password, String tenantName, ServerWebExchange exchange){
        return userService.login(userName,password,tenantName).map(jwt -> {
            exchange.getResponse().getHeaders().set(ServiceConstant.Header.X_AUTH_TOKEN, jwt);
            return R.ok();}).defaultIfEmpty(R.fail("账号密码错误"));
    }
}
