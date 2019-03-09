package com.kg.report.model.vo;

import com.kg.report.model.enums.ReportTypeEnum;
import com.kg.report.model.po.ReportPO;
import com.kg.report.utils.HasTransConsumer;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ReportDetailVO implements Serializable, HasTransConsumer<ReportPO, ReportDetailVO> {
  private static final long serialVersionUID = -5213792231944376587L;
  private int id;
  private String name;
  private int deptId;
  private ReportTypeEnum type;
  private String deadline;
  private Date addTime;
  private Date modifyTime;
  private int modifyUserId;

  private List<FieldMinVO> fields;

  @Override
  public void transAction(ReportPO source, ReportDetailVO self) {
    self.setType(ReportTypeEnum.valueOf(source.getType()));
  }

  @Override
  public void reTransAction(ReportDetailVO self, ReportPO target) {

  }

  public List<FieldMinVO> getFields() {
    return fields;
  }

  public void setFields(List<FieldMinVO> fields) {
    this.fields = fields;
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
