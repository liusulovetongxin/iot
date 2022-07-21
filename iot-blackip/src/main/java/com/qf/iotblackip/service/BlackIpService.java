package com.qf.iotblackip.service;

import com.qf.iotblackip.dto.BlackIpDto;
import com.qf.iotblackip.pojo.Dc3BlackIp;

import java.util.List;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotblackip.service
 * @Description:
 * @Date 2022/7/20 16:31
 */

public interface BlackIpService {
    void addBlackIp(Dc3BlackIp dc3BlackIp);

    List<BlackIpDto> findAllIp2Dto();
    void refresh();

    Long deleteId(String id);
}
