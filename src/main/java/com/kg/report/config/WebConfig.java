package com.kg.report.config;

import com.kg.report.config.interceptor.SessionInterceptor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableAutoConfiguration
public class WebConfig implements WebMvcConfigurer {
  /**
   * 添加自定义的Interceptors
   */
  @Bean
  SessionInterceptor sessionInterceptor() {
    return new SessionInterceptor();
  }

  /**
   * 拦截器 只拦截API接口
   *
   * @return
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(sessionInterceptor()).addPathPatterns("/api/**").excludePathPatterns("/api/login");
  }
}
