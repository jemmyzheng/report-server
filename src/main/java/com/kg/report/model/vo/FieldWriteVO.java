package com.kg.report.model.vo;

import com.kg.report.model.enums.FieldTypeEnum;

import java.io.Serializable;

public class FieldWriteVO<T> implements Serializable {
  private int fieldId;
  private String fieldName;
  private T value;
  private FieldTypeEnum type;
  public FieldWriteVO(int fieldId, String fieldName, T value) {
    this.fieldId = fieldId;
    this.fieldName = fieldName;
    this.value = value;
  }

  public FieldWriteVO(int fieldId, String fieldName, FieldTypeEnum type) {
    this.fieldId = fieldId;
    this.fieldName = fieldName;
    this.type = type;
  }

  public FieldWriteVO() {
  }

  public FieldTypeEnum getType() {
    return type;
  }

  public void setType(FieldTypeEnum type) {
    this.type = type;
  }

  public int getFieldId() {
    return fieldId;
  }

  public void setFieldId(int fieldId) {
    this.fieldId = fieldId;
  }

  public String getFieldName() {
    return fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public T getValue() {
    return value;
  }

  public void setValue(T value) {
    this.value = value;
  }
}
