package com.qf.iotforbiddenpath.service;

import com.qf.iotforbiddenpath.dto.Dc3ForbiddenPathDto;

import java.util.List;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotforbiddenpath.service
 * @Description:
 * @Date 2022/7/22 10:20
 */

public interface ForbiddenPathService {
    void addPath(Dc3ForbiddenPathDto dc3ForbiddenPathDto);

    Dc3ForbiddenPathDto findById(String id);

    List<Dc3ForbiddenPathDto> findList();

    void deleteById(String id);
    void refresh();
}
