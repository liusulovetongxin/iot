package com.qf.iottenant.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Dc3TenantBind {

  private String id;
  private String tenantId;
  private String userId;
  private String description;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;
  private Long deleted;




}
