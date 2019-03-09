package com.kg.report.model.vo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ReportValueEditVO implements Serializable {
  private static final long serialVersionUID = 677278471638685906L;
  @Positive
  private int reportId;
  @Positive
  private int airportId;
  @NotNull
  @Size(min = 1)
  private List<ValueItemVO> values;

  private boolean makeUp; // 是否是补录

  private Date makeUpDate; // 要补录的日期

  public boolean isMakeUp() {
    return makeUp;
  }

  public void setMakeUp(boolean makeUp) {
    this.makeUp = makeUp;
  }

  public Date getMakeUpDate() {
    return makeUpDate;
  }

  public void setMakeUpDate(Date makeUpDate) {
    this.makeUpDate = makeUpDate;
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

  public List<ValueItemVO> getValues() {
    return values;
  }

  public void setValues(List<ValueItemVO> values) {
    this.values = values;
  }
}
