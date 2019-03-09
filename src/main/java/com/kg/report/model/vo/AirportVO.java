package com.kg.report.model.vo;

import javax.validation.constraints.Positive;

public class AirportVO extends AirportCreatorVO {
  private static final long serialVersionUID = -332685848472719190L;
  @Positive
  private int id;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
