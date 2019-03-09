package com.kg.report.model.vo;

import com.kg.report.model.enums.ReportTypeEnum;

import java.util.List;

public class ReportCurrentVO {
  private int id;
  private String name;
  private int deptId;
  private ReportTypeEnum type;
  private String deadline;
  private List<Integer> airportInputed; // 当前录入窗口已录入过数据

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

  public List<Integer> getAirportInputed() {
    return airportInputed;
  }

  public void setAirportInputed(List<Integer> airportInputed) {
    this.airportInputed = airportInputed;
  }
}
