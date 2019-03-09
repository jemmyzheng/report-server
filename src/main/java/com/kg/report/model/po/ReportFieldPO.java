package com.kg.report.model.po;

public class ReportFieldPO {
    /**
	* 报表ID
	*/
    private Integer reportId;

    /**
	* 字段ID
	*/
    private Integer fieldId;

  public ReportFieldPO(Integer reportId, Integer fieldId) {
    this.reportId = reportId;
    this.fieldId = fieldId;
  }

  public ReportFieldPO() {
  }

  public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }
}