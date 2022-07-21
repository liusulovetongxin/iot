package com.qf.iotgateway.cache;

import com.dc3.common.constant.CacheConstant;
import com.qf.feign.CacheFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotgateway.cache
 * @Description:
 * @Date 2022/7/21 17:27
 */
@Component
public class BlackIpCache {
    private boolean isLoad;
    private CacheFeign cacheFeign;

    @Autowired
    @Lazy
    public void setCacheFeign(CacheFeign cacheFeign) {
        this.cacheFeign = cacheFeign;
    }

    private Collection blackIpSet;

    public Collection getBlackIpSet() {
        return blackIpSet;
    }

    @EventListener
    public void onEvent(ContextRefreshedEvent event){
        synchronized (cacheFeign){
            if (!isLoad){
                isLoad=true;
                initData();
            }
        }
    }
    private void initData(){
        cacheFeign.sMember("zck"+CacheConstant.Entity.BLACK_IP).subscribe(r->blackIpSet = (Collection) r.getData());
    }
    @Bean
    public Consumer<String> onBlackIpChange(){
        return msg->initData();
    }
}
