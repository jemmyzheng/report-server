package com.kg.report.model.vo;

import com.kg.report.model.enums.ValueStateEnum;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ReportValueVO<T> implements Serializable {
  private int id;
  private int reportId;
  private int fieldId;
  private String fieldName;
  private int airportId;
  private T value;
  private T modifyValue;
  private ValueStateEnum state;
  private Date addTime;
  private Date modifyTime;
  private String addDay;

  public ReportValueVO() {
  }

  public ReportValueVO(T value, T modifyValue, String fieldName) {
    this.value = value;
    this.modifyValue = modifyValue;
    this.fieldName = fieldName;
  }

  public String getAddDay() {
    return addDay;
  }

  public void setAddDay(String addDay) {
    this.addDay = addDay;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

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

  public String getFieldName() {
    return fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public int getAirportId() {
    return airportId;
  }

  public void setAirportId(int airportId) {
    this.airportId = airportId;
  }

  public T getValue() {
    return value;
  }

  public void setValue(T value) {
    this.value = value;
  }

  public T getModifyValue() {
    return modifyValue;
  }

  public void setModifyValue(T modifyValue) {
    this.modifyValue = modifyValue;
  }

  public ValueStateEnum getState() {
    return state;
  }

  public void setState(ValueStateEnum state) {
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
}
