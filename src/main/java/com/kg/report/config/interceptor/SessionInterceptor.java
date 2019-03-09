package com.kg.report.config.interceptor;

import com.kg.report.CurrentUser;
import com.kg.report.model.enums.RoleEnum;
import com.kg.report.utils.DoAuth;
import com.kg.report.utils.exception.NoAuthException;
import com.kg.report.utils.exception.SessionFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionInterceptor implements HandlerInterceptor {

  @Autowired
  CurrentUser currentUser;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws SessionFailureException, NoAuthException {
    if (currentUser.getUserId() == 0) throw new SessionFailureException(request.getMethod(), request.getRequestURI()); // 判断是否登录
    if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
      HandlerMethod handlerMethod = ((HandlerMethod) handler);
      Class<?> beanType = handlerMethod.getBeanType();
      boolean needAuth = handlerMethod.hasMethodAnnotation(DoAuth.class) || beanType.getAnnotation(DoAuth.class) != null; // 鉴权
      if (!needAuth) return true;
      if (needAuth && currentUser.getRole().equals(RoleEnum.Manager)) {
        return true;
      }
      throw new NoAuthException(beanType, handlerMethod);
    }
    return true;
  }
}
