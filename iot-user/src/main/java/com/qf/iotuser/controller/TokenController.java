package com.qf.iotuser.controller;

import com.qf.iotuser.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotuser.controller
 * @Description:
 * @Date 2022/7/19 19:21
 */
@RestController
@RequestMapping("/token")
public class TokenController {
    private TokenService tokenService;

    @Autowired
    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/check")
    public Mono<Integer> checkToken(String token){
        return tokenService.CheckToken(token);
    }

}
