package com.kg.report.model.enums;

public enum ActionCodeEnum {

  //基本结果

  // 操作失败
  Failed(0, true),
  // 操作成功
  Success(1),
  //参数错误
  ParamError(2),

  /**
   * 用户相关
   */
  UserIsExist(100),
  UserNotExist(101),
  UserLocked(102),
  PasswordError(103),
  PhoneIsExist(104),
  /**
   * 部门相关
   */
  DeptIsExist(200),
  DeptNotExist(201),
  /**
   * 报表字段
   */
  FieldIsExist(300),
  FieldNotExist(301),

  /**
   * 机场
   */
  AirportIsExist(400),
  AirportNotExist(401),

  /**
   * 报表
   */
  ReportNotExist(500),
  ReportValueIsInput(501),// 报表数据已经存在 只能更新， 不能添加
  ReportValueNotInput(501),// 报表数据不存在 得先添加， 不能更新
  ExportExcelMustWithDate(502), // 要导出excel必须选择日期

  Logout(20),
  Unknown(99999, true);

  private int value;
  private boolean internal;

  public int getValue() {
    return value;
  }

  public boolean isInternal() {
    return internal;
  }

  public String getValueStr() {
    return String.valueOf(value);
  }

  ActionCodeEnum(int value) {
    this.value = value;
    this.internal = false;
  }

  ActionCodeEnum(int value, boolean internal) {
    this.value = value;
    this.internal = internal;
  }

  public static ActionCodeEnum valueOf(int value) {
    for (ActionCodeEnum val : ActionCodeEnum.values()) {
      if (value == val.getValue()) {
        return val;
      }
    }
    return ActionCodeEnum.Unknown;
  }

  public static ActionCodeEnum nameOf(String name) {
    for (ActionCodeEnum val : ActionCodeEnum.values()) {
      if (val.name().equals(name)) {
        return val;
      }
    }
    return ActionCodeEnum.Unknown;
  }
}
