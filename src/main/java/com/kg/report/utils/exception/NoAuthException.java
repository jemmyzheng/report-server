package com.kg.report.utils.exception;

import org.springframework.web.method.HandlerMethod;

public class NoAuthException extends Exception {
  private Class<?> beanType;
  private HandlerMethod handlerMethod;

  public NoAuthException() {
    super();
  }

  public NoAuthException(Class<?> beanType, HandlerMethod handlerMethod) {
    super();
    this.beanType = beanType;
    this.handlerMethod = handlerMethod;
  }

  public Class<?> getBeanType() {
    return beanType;
  }

  public HandlerMethod getHandlerMethod() {
    return handlerMethod;
  }
}
