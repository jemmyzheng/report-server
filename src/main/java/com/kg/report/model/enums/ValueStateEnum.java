package com.kg.report.model.enums;

public enum ValueStateEnum {
  Normal((short) 0),
  Timeout((short) 1),
  Modifying((short) 2),
  Modified((short) 3),
  Unknown((short) 99);

  private final short value;

  ValueStateEnum(short value) {
    this.value = value;
  }

  public short getValue() { return value; }

  public static ValueStateEnum valueOf(short value) {
    for (ValueStateEnum val : ValueStateEnum.values()) {
      if (value == val.getValue()) {
        return val;
      }
    }
    return ValueStateEnum.Unknown;
  }
}
