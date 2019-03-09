package com.kg.report.model.po;

import java.util.Date;

public class FieldPO {
  /**
   * ID
   */
  private Integer id;

  /**
   * 字段名称
   */
  private String name;

  /**
   * 字段类型（0:整数；1:小数；2:文本）
   */
  private Short type;

  /**
   * 是否必填（0:可为空；1:必填）
   */
  private Short required;

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

  public Short getType() {
    return type;
  }

  public void setType(Short type) {
    this.type = type;
  }

  public Short getRequired() {
    return required;
  }

  public void setRequired(Short required) {
    this.required = required;
  }

  public Date getAddTime() {
    return addTime;
  }

  public void setAddTime(Date addTime) {
    this.addTime = addTime;
  }
}