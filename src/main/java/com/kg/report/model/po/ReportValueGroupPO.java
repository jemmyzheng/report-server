package com.kg.report.model.po;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReportValueGroupPO {
  private String addDay;
  /**
   * 报表ID
   */
  private String reportIdStr;
  private String airportIdStr;
  private String idStr;

  private String fieldIdStr;

  private String fieldValueStr;

  private String stateStr;

  private String modifyTimeStr;

  private String addTimeStr;

  public Map<String, String[]> help() {
    Map<String, String[]> result = new HashMap<>();
    result.put("reportId", reportIdStr.split("_"));
    result.put("airportId", airportIdStr.split("_"));
    result.put("id", idStr.split("_"));
    result.put("fieldId", fieldIdStr.split("_"));
    result.put("value", fieldValueStr.split("_"));
    result.put("state", stateStr.split("_"));
    result.put("modifyTime", modifyTimeStr.split("_"));
    result.put("addTime", addTimeStr.split("_"));
    return result;
  }

  public String getAddTimeStr() {
    return addTimeStr;
  }

  public void setAddTimeStr(String addTimeStr) {
    this.addTimeStr = addTimeStr;
  }

  public String getAddDay() {
    return addDay;
  }

  public void setAddDay(String addDay) {
    this.addDay = addDay;
  }

  public String getReportIdStr() {
    return reportIdStr;
  }

  public void setReportIdStr(String reportIdStr) {
    this.reportIdStr = reportIdStr;
  }

  public String getAirportIdStr() {
    return airportIdStr;
  }

  public void setAirportIdStr(String airportIdStr) {
    this.airportIdStr = airportIdStr;
  }

  public String getIdStr() {
    return idStr;
  }

  public void setIdStr(String idStr) {
    this.idStr = idStr;
  }

  public String getFieldIdStr() {
    return fieldIdStr;
  }

  public void setFieldIdStr(String fieldIdStr) {
    this.fieldIdStr = fieldIdStr;
  }

  public String getFieldValueStr() {
    return fieldValueStr;
  }

  public void setFieldValueStr(String fieldValueStr) {
    this.fieldValueStr = fieldValueStr;
  }

  public String getStateStr() {
    return stateStr;
  }

  public void setStateStr(String stateStr) {
    this.stateStr = stateStr;
  }

  public String getModifyTimeStr() {
    return modifyTimeStr;
  }

  public void setModifyTimeStr(String modifyTimeStr) {
    this.modifyTimeStr = modifyTimeStr;
  }
}
