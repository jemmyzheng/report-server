package com.kg.report;

import com.kg.report.model.enums.ActionCodeEnum;
import com.kg.report.utils.ApiResult;
import com.kg.report.utils.exception.NoAuthException;
import com.kg.report.utils.exception.ServiceActionException;
import com.kg.report.utils.exception.SessionFailureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  public static String INTERNAL_SERVER_ERROR = "Internal Server Error";

  protected Logger logger;

  public RestExceptionHandler() {
    this.logger = LoggerFactory.getLogger(getClass());
  }

  /**
   * 捕获 MethodArgumentNotValidException. 当请求携带的参数 @Valid validation 失败时.
   *
   * @param ex      MethodArgumentNotValidException 当 @Valid validation 结果失败时抛出
   * @param headers HttpHeaders
   * @param status  HttpStatus
   * @param request WebRequest
   * @return the ApiResult object with isSuccess false
   */
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return buildResponseEntity(ApiResult.hasError(ex.getBindingResult().getFieldErrors(), status));
  }

  @Override
  protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    return buildResponseEntity(ApiResult.hasError(ex.getBindingResult().getFieldErrors(), status));
  }

  /**
   * 捕获 NoHandlerFoundException，当请求404时
   *
   * @param ex      NoHandlerFoundException 请求当URL地址触发404时 抛出
   * @param headers HttpHeaders
   * @param status  HttpStatus
   * @param request WebRequest
   * @return
   */
  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    return buildResponseEntity(ApiResult.hasError(ex.getMessage(), status));
  }

  /**
   * 捕获 HttpRequestMethodNotSupportedException，当请求的HttpMethod 未配置时
   *
   * @param ex      HttpRequestMethodNotSupportedException 当请求的HttpMethod 未配置时 抛出
   * @param headers HttpHeaders
   * @param status  HttpStatus
   * @param request WebRequest
   * @return
   */
  @Override
  protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
      HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    return buildResponseEntity(ApiResult.hasError(ex.getMessage(), status));
  }

  /**
   * 捕获 ServiceActionException， 当后端接口返回操作失败时
   *
   * @param ex      ServiceActionException 后端接口操作失败将抛出 并携带ActionCode
   * @param request WebRequest
   * @return
   */
  @ExceptionHandler(ServiceActionException.class)
  protected ResponseEntity<Object> handleDubboServiceException(ServiceActionException ex, WebRequest request) {
    ActionCodeEnum actionCode = ex.getActionCode();
    HttpStatus status;
    if (actionCode.isInternal()) {
      status = HttpStatus.INTERNAL_SERVER_ERROR;
      logger.warn("Service action error with action code : " + ex.getActionCode(), ex);
    } else if (actionCode == ActionCodeEnum.Logout){
      status = HttpStatus.UNAUTHORIZED;
    } else {
      status = HttpStatus.OK;
    }
    return buildResponseEntity(ApiResult.hasError(actionCode.name(), status));
  }

  /**
   * 捕获 SessionFailureException，Session失效时
   *
   * @param ex SessionFailureException
   * @return
   */
  @ExceptionHandler(SessionFailureException.class)
  protected ResponseEntity<Object> handleSessionFailureException(SessionFailureException ex) {
    return buildResponseEntity(ApiResult.hasError(ActionCodeEnum.Logout, HttpStatus.UNAUTHORIZED));
  }


  /**
   * 捕获无权限
   * @param ex
   * @return
   */
  @ExceptionHandler(NoAuthException.class)
  protected ResponseEntity<Object> handleNoAuthException(NoAuthException ex) {
    return buildResponseEntity(ApiResult.hasError("un authorized", HttpStatus.FORBIDDEN));
  }

  /**
   * 捕获未知错误
   *
   * @param request HttpServletRequest
   * @param ex      Exception
   * @return
   */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  protected ResponseEntity<Object> handleUnknowException(HttpServletRequest request, Exception ex) {
    logger.error("Catch UnKnow Exception When Visiting " + request.getRequestURL().toString(), ex);
    return buildResponseEntity(ApiResult.hasError(INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR));
  }

  /**
   * 构造返回的 ResponseEntity
   *
   * @param result ApiResult
   * @return
   */
  private ResponseEntity<Object> buildResponseEntity(ApiResult result) {
    return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
  }
}
