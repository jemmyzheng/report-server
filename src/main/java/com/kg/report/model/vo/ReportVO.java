package com.kg.report.model.vo;

import com.kg.report.model.enums.ReportTypeEnum;
import com.kg.report.model.po.ReportPO;
import com.kg.report.utils.HasTransConsumer;

import java.io.Serializable;
import java.util.Date;

public class ReportVO implements Serializable, HasTransConsumer<ReportPO, ReportVO> {
  private static final long serialVersionUID = -6346972106607412439L;
  private int id;
  private String name;
  private int deptId;
  private ReportTypeEnum type;
  private String deadline;
  private Date addTime;
  private Date modifyTime;
  private int modifyUserId;

  @Override
  public void transAction(ReportPO source, ReportVO self) {
    self.setType(ReportTypeEnum.valueOf(source.getType()));
  }

  @Override
  public void reTransAction(ReportVO self, ReportPO target) {

  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getDeptId() {
    return deptId;
  }

  public void setDeptId(int deptId) {
    this.deptId = deptId;
  }

  public ReportTypeEnum getType() {
    return type;
  }

  public void setType(ReportTypeEnum type) {
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

  public int getModifyUserId() {
    return modifyUserId;
  }

  public void setModifyUserId(int modifyUserId) {
    this.modifyUserId = modifyUserId;
  }
}
