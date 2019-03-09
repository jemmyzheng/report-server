package com.kg.report.model.vo;

import java.io.Serializable;
import java.util.Date;

public class ReportDataSearcher implements Serializable {
  private int[] reportId = {};
  private int airportId;
  private Date beginDate;
  private Date endDate;

  public int[] getReportId() {
    return reportId;
  }

  public void setReportId(int[] reportId) {
    this.reportId = reportId;
  }

  public int getAirportId() {
    return airportId;
  }

  public void setAirportId(int airportId) {
    this.airportId = airportId;
  }

  public Date getBeginDate() {
    return beginDate;
  }

  public void setBeginDate(Date beginDate) {
    this.beginDate = beginDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
}
