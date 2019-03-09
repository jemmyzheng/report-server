package com.kg.report.model.enums;

public enum ReportTypeEnum {
  Daily((short) 0),
  Weekly((short) 1),
  Monthly((short) 2),
  Annually((short) 3),
  Unknown((short) 99);

  private final short value;

  ReportTypeEnum(short value) {
    this.value = value;
  }

  public short getValue() { return value; }

  public static ReportTypeEnum valueOf(short value) {
    for (ReportTypeEnum val : ReportTypeEnum.values()) {
      if (value == val.getValue()) {
        return val;
      }
    }
    return ReportTypeEnum.Unknown;
  }
}
