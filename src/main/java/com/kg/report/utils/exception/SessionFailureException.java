package com.kg.report.utils.exception;

public class SessionFailureException extends Exception {
  private String requestMethod;
  private String requestURI;

  public SessionFailureException(String requestMethod, String requestURI) {
    super();
    this.requestMethod = requestMethod;
    this.requestURI = requestURI;
  }

  public String getRequestMethod() {
    return requestMethod;
  }

  public String getRequestURI() {
    return requestURI;
  }
}
