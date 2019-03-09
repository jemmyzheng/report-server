package com.kg.report.model.vo;

import java.io.Serializable;
import java.util.Date;

public class ReportDataQueryVO implements Serializable {
  private int deptId;
  private int reportId;
  private int airportId;
  private Date begin;
  private Date end;

  public int getDeptId() {
    return deptId;
  }

  public void setDeptId(int deptId) {
    this.deptId = deptId;
  }

  public int getReportId() {
    return reportId;
  }

  public void setReportId(int reportId) {
    this.reportId = reportId;
  }

  public int getAirportId() {
    return airportId;
  }

  public void setAirportId(int airportId) {
    this.airportId = airportId;
  }

  public Date getBegin() {
    return begin;
  }

  public void setBegin(Date begin) {
    this.begin = begin;
  }

  public Date getEnd() {
    return end;
  }

  public void setEnd(Date end) {
    this.end = end;
  }
}
