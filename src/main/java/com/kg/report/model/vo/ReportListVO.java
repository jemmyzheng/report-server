package com.kg.report.model.vo;

import com.kg.report.model.enums.ReportTypeEnum;
import com.kg.report.model.po.ReportFieldListPO;
import com.kg.report.utils.HasTransConsumer;

import java.io.Serializable;
import java.util.List;

public class ReportListVO implements Serializable, HasTransConsumer<ReportFieldListPO, ReportListVO> {
  private static final long serialVersionUID = 5827063210931976403L;
  private int id;
  private String name;
  private ReportTypeEnum type;
  private String deadline;
  private List<Integer> fields;

  @Override
  public void transAction(ReportFieldListPO source, ReportListVO self) {
    self.setType(ReportTypeEnum.valueOf(source.getType()));
  }

  @Override
  public void reTransAction(ReportListVO self, ReportFieldListPO target) {

  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ReportTypeEnum getType() {
    return type;
  }

  public void setType(ReportTypeEnum type) {
    this.type = type;
  }

  public String getDeadline() {
    return deadline;
  }

  public void setDeadline(String deadline) {
    this.deadline = deadline;
  }

  public List<Integer> getFields() {
    return fields;
  }

  public void setFields(List<Integer> fields) {
    this.fields = fields;
  }
}
