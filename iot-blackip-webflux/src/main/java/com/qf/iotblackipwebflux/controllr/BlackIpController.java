package com.qf.iotblackipwebflux.controllr;

import com.dc3.common.bean.R;
import com.qf.iotblackipwebflux.dto.BlackIpDto;
import com.qf.iotblackipwebflux.service.BlackIpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotblackipwebflux.controllr
 * @Description:
 * @Date 2022/7/22 15:28
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
    public Mono<R<Object>> addBlackIp(@RequestBody Mono<BlackIpDto> blackIpDtoMono){
        return blackIpService.addBlackIp(blackIpDtoMono)
                .map(result->R.ok()).defaultIfEmpty(R.fail("数据错误"));
    }
    @GetMapping("/list")
    public Mono<R> findList(){
        return blackIpService.findList().map(result->R.ok(result));
    }

    @PostMapping("/{id}")
    public Mono<R> deleteById(@PathVariable String id){
        return blackIpService.updateById(id).map(integer -> R.ok(integer));
    }
}
