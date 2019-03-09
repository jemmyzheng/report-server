package com.kg.report.utils.exception;


import com.kg.report.model.enums.ActionCodeEnum;

/**
 * Service 操作失败后，直接在service层抛出该 Exception
 */
public class ServiceActionException extends Exception {

  private ActionCodeEnum actionCode;

  public ServiceActionException(ActionCodeEnum actionCode) {
    super("ActionCode: " + actionCode.name());
    this.actionCode = actionCode;
  }

  public ActionCodeEnum getActionCode() {
    return actionCode;
  }
}
