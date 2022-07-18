package com.qf.iotuser.event;

import com.qf.feign.TenantFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotuser.event
 * @Description:
 * @Date 2022/7/18 17:52
 */
@Component
public class BIndUserListener {
    private TenantFeign tenantFeign;

    @Autowired
    public void setTenantFeign(TenantFeign tenantFeign) {
        this.tenantFeign = tenantFeign;
    }

    @EventListener
    public void  onEvent(BindUserEvent event){
        Map data = event.getData();
        tenantFeign.bindTenant2User(data);
    }
}
