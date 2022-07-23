package com.qf.iotblackipwebflux.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Dc3BlackIp {

  private String id;
  private String ip;
  private Long enable;
  private String description;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;
  private Long deleted;




}
