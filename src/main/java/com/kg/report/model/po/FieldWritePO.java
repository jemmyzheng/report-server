package com.kg.report.model.po;

public class FieldWritePO {
  private Integer id;

  /**
   * 报表ID
   */
  private Integer reportId;

  /**
   * 字段ID
   */
  private Integer fieldId;

  /**
   * 机场ID
   */
  private Integer airportId;

  /**
   * 字段值
   */
  private String value;

  private String airportName;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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

  public Integer getAirportId() {
    return airportId;
  }

  public void setAirportId(Integer airportId) {
    this.airportId = airportId;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getAirportName() {
    return airportName;
  }

  public void setAirportName(String airportName) {
    this.airportName = airportName;
  }
}
