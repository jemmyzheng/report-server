package com.kg.report.model.vo;

import com.kg.report.model.enums.ReportTypeEnum;
import com.kg.report.model.po.ReportPO;
import com.kg.report.utils.HasTransConsumer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

public class ReportCreatorVO implements Serializable, HasTransConsumer<ReportPO, ReportCreatorVO> {
  private static final long serialVersionUID = -6034334072580493038L;
  @NotBlank
  private String name;
  @Positive
  private int deptId;
  @NotNull
  private ReportTypeEnum type;
  @NotNull
  private String deadline;

  @NotNull
  @Size(min = 1)
  private List<Integer> fields;

  @Override
  public void transAction(ReportPO source, ReportCreatorVO self) {

  }

  @Override
  public void reTransAction(ReportCreatorVO self, ReportPO target) {
    target.setType(self.getType().getValue());
  }

  public List<Integer> getFields() {
    return fields;
  }

  public void setFields(List<Integer> fields) {
    this.fields = fields;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getDeptId() {
    return deptId;
  }

  public void setDeptId(int deptId) {
    this.deptId = deptId;
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
}
