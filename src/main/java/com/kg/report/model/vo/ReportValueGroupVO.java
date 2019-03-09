package com.kg.report.model.vo;

import java.io.Serializable;
import java.util.List;

public class ReportValueGroupVO implements Serializable {
  private static final long serialVersionUID = 1098068324192556458L;
  private String addDay;
  private List<ReportValueVO> values;

  public String getAddDay() {
    return addDay;
  }

  public void setAddDay(String addDay) {
    this.addDay = addDay;
  }

  public List<ReportValueVO> getValues() {
    return values;
  }

  public void setValues(List<ReportValueVO> values) {
    this.values = values;
  }
}
