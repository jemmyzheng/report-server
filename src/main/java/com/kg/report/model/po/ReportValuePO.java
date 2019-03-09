package com.kg.report.model.po;

import java.util.Date;

public class ReportValuePO {

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

  /**
   * 待修改值
   */
  private String modifyValue;

  /**
   * 状态(0:正常，1:超时，2:有修改)
   */
  private Short state;

  /**
   * 录入时间
   */
  private Date addTime;

  /**
   * 最后修改时间
   */
  private Date modifyTime;

  /**
   * 最后修改人ID
   */
  private Integer modifyUserId;

  /**
   * 录入的当天（用以分组数据）
   */
  private String addDay;

  public String getAddDay() {
    return addDay;
  }

  public void setAddDay(String addDay) {
    this.addDay = addDay;
  }

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

  public String getModifyValue() {
    return modifyValue;
  }

  public void setModifyValue(String modifyValue) {
    this.modifyValue = modifyValue;
  }

  public Short getState() {
    return state;
  }

  public void setState(Short state) {
    this.state = state;
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

  public Integer getModifyUserId() {
    return modifyUserId;
  }

  public void setModifyUserId(Integer modifyUserId) {
    this.modifyUserId = modifyUserId;
  }
}