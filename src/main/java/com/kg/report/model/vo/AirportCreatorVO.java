package com.kg.report.model.vo;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class AirportCreatorVO implements Serializable {
  private static final long serialVersionUID = 6648842115316748422L;
  @NotBlank
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
