package com.qf.iotgateway.cache;

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
 * @Date 2022/7/22 11:24
 */
@Component
public class ForbiddenPathCache {
    private boolean isLoad;
    private CacheFeign cacheFeign;

    @Autowired
    @Lazy
    public void setCacheFeign(CacheFeign cacheFeign) {
        this.cacheFeign = cacheFeign;
    }
//    private Set pathCacheSet = new HashSet();
//
//    public Set getPathCacheSet() {
//        return pathCacheSet;
//    }
    private Collection pathCacheSet ;

    public Collection getPathCacheSet() {
        return pathCacheSet;
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
        cacheFeign.sMember("zck-forbidden-path").subscribe(r ->{
         pathCacheSet = ((Collection) r.getData());
        });
    }

    @Bean
    public Consumer<String>  onForbiddenPathChange(){
        return msg->initData();
    }

}
