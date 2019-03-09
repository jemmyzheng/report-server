package com.kg.report.model.po;

import java.util.ArrayList;
import java.util.List;

public class ReportFieldListPO {
  /**
   * ID
   */
  private Integer id;

  /**
   * 报表名称
   */
  private String name;

  /**
   * 报表类型（0:日报；1:周报；2:月报；3：年报）
   */
  private Short type;

  /**
   * 数据录入的截止时间（MM-WW-DD-HH-mm）
   */
  private String deadline;

  /**
   * 一组字段ID组合成的字符串
   */
  private String fieldIdStr;

  public List<Integer> getFields() {
    String[] a = fieldIdStr.split("_");
    List<Integer> result = new ArrayList<>();
    for (int i = 0; i < a.length; i++) {
      result.add(Integer.valueOf(a[i]));
    }
    return result;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Short getType() {
    return type;
  }

  public void setType(Short type) {
    this.type = type;
  }

  public String getDeadline() {
    return deadline;
  }

  public void setDeadline(String deadline) {
    this.deadline = deadline;
  }

  public String getFieldIdStr() {
    return fieldIdStr;
  }

  public void setFieldIdStr(String fieldIdStr) {
    this.fieldIdStr = fieldIdStr;
  }
}
