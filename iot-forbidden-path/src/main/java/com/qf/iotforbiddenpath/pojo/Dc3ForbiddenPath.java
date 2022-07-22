package com.qf.iotforbiddenpath.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Dc3ForbiddenPath {

  private String id;
  private String urlPath;
  private Long enable;
  private String description;
  private java.util.Date createTime;
  private java.util.Date updateTime;
  private Long deleted;

}
