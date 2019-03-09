package com.kg.report.model.enums;

public enum RoleEnum {
  Normal((short) 0),
  Manager((short) 1),
  Unknown((short) 99);

  private final short value;

  RoleEnum(short value) {
    this.value = value;
  }

  public short getValue() { return value; }

  public static RoleEnum valueOf(short value) {
    for (RoleEnum val : RoleEnum.values()) {
      if (value == val.getValue()) {
        return val;
      }
    }
    return RoleEnum.Unknown;
  }
}
