package com.kg.report.model.vo;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class DeptCreatorVO implements Serializable {
  @NotBlank
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
