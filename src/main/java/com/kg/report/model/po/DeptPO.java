package com.kg.report.model.po;

import java.util.Date;

public class DeptPO {
  /**
   * ID
   */
  private Integer id;

  /**
   * 部门名称
   */
  private String name;

  /**
   * 创建时间
   */
  private Date addTime;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getAddTime() {
    return addTime;
  }

  public void setAddTime(Date addTime) {
    this.addTime = addTime;
  }
}