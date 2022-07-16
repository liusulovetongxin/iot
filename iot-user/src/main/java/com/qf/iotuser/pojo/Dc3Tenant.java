package com.qf.iotuser.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Dc3Tenant {

  private String id;
  private String name;
  private Long enable;
  private String description;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;
  private Long deleted;
  private List<Dc3User> users;

}
