package com.qf.iotblackipwebflux.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotblackip.dto
 * @Description:
 * @Date 2022/7/20 16:39
 */
@Data
@ToString
public class BlackIpDto {
    private String ip;
    private String description;
}
