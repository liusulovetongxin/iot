package com.qf.feign;

import com.dc3.common.bean.R;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.feign
 * @Description:
 * @Date 2022/7/15 19:31
 */
@EnableFeignClients(value = "zck-iot-user")
public interface UserFeign {

    @GetMapping("/user/info/{id}")
    R findById(@PathVariable String id);

    @PostMapping("/user/list")
    R findByIdIn(@RequestBody List<String> ids);

    @GetMapping("/token/check")
    R checkToken(@RequestParam String token);
}
