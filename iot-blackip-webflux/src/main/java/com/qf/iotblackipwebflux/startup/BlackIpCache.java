package com.qf.iotblackipwebflux.startup;

import com.qf.feign.CacheFeign;
import com.qf.iotblackipwebflux.events.BlackIpChangeEvent;
import com.qf.iotblackipwebflux.service.BlackIpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotblackipwebflux.startup
 * @Description:
 * @Date 2022/7/22 16:20
 */
@Component
public class BlackIpCache {
    private StreamBridge streamBridge;
    @Autowired
    public void setStreamBridge(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    private boolean isLoad;
    private BlackIpService blackIpService;

    @Autowired
    public void setBlackIpService(BlackIpService blackIpService) {
        this.blackIpService = blackIpService;
    }

    private CacheFeign cacheFeign;

    @Autowired
    public void setCacheFeign(CacheFeign cacheFeign) {
        this.cacheFeign = cacheFeign;
    }

    @EventListener
    public void onEvent(ContextRefreshedEvent event){
        synchronized (cacheFeign){
            if (!isLoad) {
                isLoad=true;
                initData();
            }
        }
    }
    public void initData(){
        blackIpService.findList()
                .subscribe(list->{
                    HashSet<String> keys = new HashSet<>();
                    keys.add("zck_iot_blackip_webflux");
                    cacheFeign.deleteKeys(keys)
                                    .subscribe(s->cacheFeign.sAdd("zck_iot_blackip_webflux", list.stream().collect(Collectors.toSet())).subscribe(s1->
                                            streamBridge.send("zckblackipwebfluxchange","")
                                    ));
                });
    }
    @EventListener
    public void onEvent(BlackIpChangeEvent event){
//        System.err.println("触发监听");
        initData();
    }
}
