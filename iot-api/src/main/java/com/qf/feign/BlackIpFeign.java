package com.qf.feign;

import com.dc3.common.bean.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.feign
 * @Description:
 * @Date 2022/7/20 17:22
 */
@FeignClient(value = "zck-iot-blackip")
public interface BlackIpFeign {
    @GetMapping("/blackip/list/simple")
    R findAllIp2Dto();
}
