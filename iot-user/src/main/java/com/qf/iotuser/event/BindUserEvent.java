package com.qf.iotuser.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

/**
 * @author Administrator
 * @version V1.0
 * @Project iot
 * @Package com.qf.iotuser.event
 * @Description:
 * @Date 2022/7/18 17:51
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BindUserEvent {
    private Map data;
}
