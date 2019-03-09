package com.kg.report.model.enums;

public enum FieldTypeEnum {
  Integers((short) 0),
  Float((short) 1),
  String((short) 2),
  Unknown((short) 99);

  private final short value;

  FieldTypeEnum(short value) {
    this.value = value;
  }

  public short getValue() { return value; }

  public static FieldTypeEnum valueOf(short value) {
    for (FieldTypeEnum val : FieldTypeEnum.values()) {
      if (value == val.getValue()) {
        return val;
      }
    }
    return FieldTypeEnum.Unknown;
  }
}
