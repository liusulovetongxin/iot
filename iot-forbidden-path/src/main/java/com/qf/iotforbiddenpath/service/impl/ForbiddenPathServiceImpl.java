package com.qf.iotforbiddenpath.service.impl;

import com.qf.iotforbiddenpath.dto.Dc3ForbiddenPathDto;
import com.qf.iotforbiddenpath.events.ForbiddenPathChangeEvent;
import com.qf.iotforbiddenpath.mapper.ForbiddenPathMapper;
import com.qf.iotforbiddenpath.pojo.Dc3ForbiddenPath;
import com.qf.iotforbiddenpath.service.ForbiddenPathService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotforbiddenpath.service.impl
 * @Description:
 * @Date 2022/7/22 10:20
 */
@Service
@Transactional
public class ForbiddenPathServiceImpl implements ForbiddenPathService {
    private ApplicationContext context;

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    private ForbiddenPathMapper forbiddenPathMapper;

    @Autowired
    public void setForbiddenPathMapper(ForbiddenPathMapper forbiddenPathMapper) {
        this.forbiddenPathMapper = forbiddenPathMapper;
    }

    @Override
    public void addPath(Dc3ForbiddenPathDto dc3ForbiddenPathDto) {
        Dc3ForbiddenPath dc3ForbiddenPath = new Dc3ForbiddenPath();
        BeanUtils.copyProperties(dc3ForbiddenPathDto,dc3ForbiddenPath);
        forbiddenPathMapper.addPath(dc3ForbiddenPath);

    }

    @Override
    public Dc3ForbiddenPathDto findById(String id) {
        return forbiddenPathMapper.findById(id);
    }

    @Override
    public List<Dc3ForbiddenPathDto> findList() {
        return forbiddenPathMapper.findList();
    }

    @Override
    public void deleteById(String id) {
        forbiddenPathMapper.deleteById(id);
    }

    @Override
    public void refresh() {
        context.publishEvent(new ForbiddenPathChangeEvent());
    }

}
