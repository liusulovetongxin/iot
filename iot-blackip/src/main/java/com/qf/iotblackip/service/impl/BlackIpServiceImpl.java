package com.qf.iotblackip.service.impl;

import com.dc3.common.exception.AddException;
import com.qf.iotblackip.dto.BlackIpDto;
import com.qf.iotblackip.mapper.BlackIpMapper;
import com.qf.iotblackip.pojo.Dc3BlackIp;
import com.qf.iotblackip.service.BlackIpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotblackip.service.impl
 * @Description:
 * @Date 2022/7/20 16:32
 */
@Service
@Transactional
public class BlackIpServiceImpl implements BlackIpService {
    private BlackIpMapper blackIpMapper;

    @Autowired
    public void setBlackIpMapper(BlackIpMapper blackIpMapper) {
        this.blackIpMapper = blackIpMapper;
    }

    @Override
    public void addBlackIp(Dc3BlackIp dc3BlackIp) {
        Assert.isTrue(StringUtils.hasText(dc3BlackIp.getIp()),()->{
           throw new AddException("出现错误%s", "ip为空");
        });
        blackIpMapper.addBlackIp(dc3BlackIp);
    }

    @Override
    public List<BlackIpDto> findAllIp2Dto() {
        return blackIpMapper.findAllIp2Dto();
    }
}
