package com.kg.report.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiResult<T> implements Serializable {

  private boolean isSuccess;

  private T data;

  private int status;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
  private LocalDateTime timestamp;

  public ApiResult() {
    timestamp = LocalDateTime.now();
  }

  public ApiResult(boolean isSuccess, T data) {
    this();
    this.isSuccess = isSuccess;
    this.data = data;
  }

  public static ApiResult hasError(String message) {
    return hasError(message, null);
  }

  public static ApiResult hasError(List<FieldError> errors, HttpStatus status) {
    Map errorMsg = new HashMap<String, String>();
    for (FieldError error : errors) {
      errorMsg.put(error.getField(), error.getDefaultMessage());
    }
    return hasError(errorMsg, status);
  }

  @SuppressWarnings("unchecked")
  public static ApiResult hasError(String message, HttpStatus status) {
    ApiResult result = new ApiResult(false, message);
    if (status != null) {
      result.setStatus(status.value());
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  public static <T> ApiResult hasError(T data, HttpStatus status) {
    ApiResult result = new ApiResult(false, data);
    result.setStatus(status.value());
    return result;
  }

  public static ApiResult hasDone() {
    return hasDone(null);
  }

  public static <T> ApiResult hasDone(T data) {
    ApiResult<T> result = new ApiResult<>(true, data);
    result.setStatus(HttpStatus.OK.value());
    return result;
  }

  public boolean isSuccess() {
    return isSuccess;
  }

  public void setSuccess(boolean success) {
    this.isSuccess = success;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = LocalDateTime.parse(timestamp);
  }
}
