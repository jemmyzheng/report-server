package com.kg.report.model.po;

import java.util.Date;

public class ReportPO {
  /**
   * ID
   */
  private Integer id;

  /**
   * 报表名称
   */
  private String name;

  /**
   * 所属部门
   */
  private Integer deptId;

  /**
   * 报表类型（0:日报；1:周报；2:月报；3：年报）
   */
  private Short type;

  /**
   * 数据录入的截止时间（MM-WW-DD-HH-mm）
   */
  private String deadline;

  /**
   * 创建时间
   */
  private Date addTime;

  /**
   * 最后修改时间
   */
  private Date modifyTime;

  /**
   * 最后修改人ID
   */
  private Integer modifyUserId;

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

  public Integer getDeptId() {
    return deptId;
  }

  public void setDeptId(Integer deptId) {
    this.deptId = deptId;
  }

  public Short getType() {
    return type;
  }

  public void setType(Short type) {
    this.type = type;
  }

  public String getDeadline() {
    return deadline;
  }

  public void setDeadline(String deadline) {
    this.deadline = deadline;
  }

  public Date getAddTime() {
    return addTime;
  }

  public void setAddTime(Date addTime) {
    this.addTime = addTime;
  }

  public Date getModifyTime() {
    return modifyTime;
  }

  public void setModifyTime(Date modifyTime) {
    this.modifyTime = modifyTime;
  }

  public Integer getModifyUserId() {
    return modifyUserId;
  }

  public void setModifyUserId(Integer modifyUserId) {
    this.modifyUserId = modifyUserId;
  }
}