package com.qf.iotblackip.startup;

import com.dc3.common.constant.CacheConstant;
import com.qf.feign.CacheFeign;
import com.qf.iotblackip.dto.BlackIpDto;
import com.qf.iotblackip.events.CacheRefreshEvent;
import com.qf.iotblackip.service.BlackIpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotblackip.startup
 * @Description:
 * @Date 2022/7/21 11:04
 */
@Component
public class Load2IpCache {
    private StreamBridge streamBridge;

    @Autowired
    public void setStreamBridge(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    private boolean isLoad;

    private CacheFeign cacheFeign;

    @Autowired
    public void setCacheFeign(CacheFeign cacheFeign) {
        this.cacheFeign = cacheFeign;
    }

    private BlackIpService blackIpService;

    @Autowired
    public void setBlackIpService(BlackIpService blackIpService) {
        this.blackIpService = blackIpService;
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
        List<BlackIpDto> allIp = blackIpService.findAllIp2Dto();
        HashSet keys = new HashSet();
        String key = "zck"+ CacheConstant.Entity.BLACK_IP;
        keys.add(key);
        cacheFeign.deleteKeys(keys);
        cacheFeign.sAdd(key, allIp.stream().map(BlackIpDto::getIp).collect(Collectors.toSet()));
        streamBridge.send("zckblackipchange", "");
    }
    @EventListener
    public void onEvent(CacheRefreshEvent event){
        initData();
    }
}
