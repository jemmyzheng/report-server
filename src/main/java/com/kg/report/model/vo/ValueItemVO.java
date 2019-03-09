package com.kg.report.model.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.io.Serializable;

public class ValueItemVO implements Serializable {
  private int id;
  @Positive
  private int fieldId;
  @NotBlank
  private String value;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getFieldId() {
    return fieldId;
  }

  public void setFieldId(int fieldId) {
    this.fieldId = fieldId;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
