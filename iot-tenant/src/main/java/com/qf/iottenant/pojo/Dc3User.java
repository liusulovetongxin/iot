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
public class Dc3User {

  private String id;
  private String name;
  private String phone;
  private String email;
  private String password;
  private Long enable;
  private String description;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;
  private Long deleted;

  private Dc3Tenant dc3Tenant;
}
