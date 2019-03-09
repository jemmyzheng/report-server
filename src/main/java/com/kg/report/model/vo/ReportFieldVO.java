package com.kg.report.model.vo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

public class ReportFieldVO implements Serializable {
  private static final long serialVersionUID = 5678588685909432429L;
  @Positive
  private int reportId;
  @NotNull
  @Size(min = 1)
  private List<Integer> fieldIds;

  public int getReportId() {
    return reportId;
  }

  public void setReportId(int reportId) {
    this.reportId = reportId;
  }

  public List<Integer> getFieldIds() {
    return fieldIds;
  }

  public void setFieldIds(List<Integer> fieldIds) {
    this.fieldIds = fieldIds;
  }

}
