package com.kg.report.model.vo;

import javax.validation.constraints.Positive;
import java.io.Serializable;

public class ReportRemoveFieldVO implements Serializable {
  private static final long serialVersionUID = 8767952191640698193L;
  @Positive
  private int reportId;
  @Positive
  private int fieldId;

  public int getReportId() {
    return reportId;
  }

  public void setReportId(int reportId) {
    this.reportId = reportId;
  }

  public int getFieldId() {
    return fieldId;
  }

  public void setFieldId(int fieldId) {
    this.fieldId = fieldId;
  }
}
