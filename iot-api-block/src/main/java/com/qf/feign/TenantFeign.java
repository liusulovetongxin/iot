package com.qf.feign;

import com.dc3.common.bean.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.feign
 * @Description:
 * @Date 2022/7/16 14:48
 */
@FeignClient(value = "zck-iot-tenant")
public interface TenantFeign {

    @GetMapping("/tenant/info/{id}")
    R findById(@PathVariable String id);

    @GetMapping("/tenant/findusers/{tenantId}")
    R findByIdIn(@PathVariable String tenantId);

    @PostMapping("/tenant/count")
    R findCount(@RequestParam("tenantId") String tenantId, @RequestBody List<String> ids);
    @PostMapping("/tenant/bind")
    R bindTenant2User(@RequestBody Map tenantBindMono);
}
