package com.kg.report.model.enums;

public enum UserStateEnum {
  Valid((short) 1),
  Locked((short) 2),
  Deleted((short) 0),
  Unknown((short) 99);

  private final short value;

  UserStateEnum(short value) {
    this.value = value;
  }

  public short getValue() { return value; }

  public static UserStateEnum valueOf(short value) {
    for (UserStateEnum val : UserStateEnum.values()) {
      if (value == val.getValue()) {
        return val;
      }
    }
    return UserStateEnum.Unknown;
  }
}
