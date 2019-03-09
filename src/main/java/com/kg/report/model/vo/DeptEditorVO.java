package com.kg.report.model.vo;

import javax.validation.constraints.Positive;

public class DeptEditorVO extends DeptCreatorVO {
  private static final long serialVersionUID = 5084111920271920773L;
  @Positive
  private int id;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
