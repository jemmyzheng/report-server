package com.kg.report.model.vo;

import com.kg.report.model.enums.ReportTypeEnum;
import com.kg.report.model.po.ReportPO;
import com.kg.report.utils.HasTransConsumer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

public class ReportEditorVO implements Serializable, HasTransConsumer<ReportPO, ReportEditorVO> {
  private static final long serialVersionUID = -2130979202965004810L;
  @Positive
  private int id;
  @NotBlank
  private String name;
  @Positive
  private int deptId;
  @NotNull
  private ReportTypeEnum type;
  @NotNull
  private String deadline;

  @Override
  public void transAction(ReportPO source, ReportEditorVO self) {

  }

  @Override
  public void reTransAction(ReportEditorVO self, ReportPO target) {
    target.setType(self.getType().getValue());
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
