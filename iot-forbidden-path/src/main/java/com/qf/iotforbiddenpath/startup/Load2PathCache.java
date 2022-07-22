package com.qf.iotforbiddenpath.startup;

import com.qf.feign.CacheFeign;
import com.qf.iotforbiddenpath.dto.Dc3ForbiddenPathDto;
import com.qf.iotforbiddenpath.events.ForbiddenPathChangeEvent;
import com.qf.iotforbiddenpath.service.ForbiddenPathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotforbiddenpath.startup
 * @Description:
 * @Date 2022/7/22 10:59
 */
@Component
public class Load2PathCache {
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

    private ForbiddenPathService forbiddenPathService;

    @Autowired
    public void setForbiddenPathService(ForbiddenPathService forbiddenPathService) {
        this.forbiddenPathService = forbiddenPathService;
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
    public void initData(){
        Set<String> urlPath = forbiddenPathService.findList()
                .stream()
                .map(Dc3ForbiddenPathDto::getUrlPath)
                .collect(Collectors.toSet());
        HashSet keys = new HashSet();
        keys.add("zck-forbidden-path");
        cacheFeign.deleteKeys(keys);
        cacheFeign.sAdd("zck-forbidden-path", urlPath);
        streamBridge.send("zckforbiddenpathchange", "");
    }
    @EventListener
    public void onEvent(ForbiddenPathChangeEvent event){
        initData();
    }
}
