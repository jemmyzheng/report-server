package com.kg.report.config;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;

import javax.servlet.ServletContext;

@Configuration
public class WebInitializer implements ServletContextInitializer {
  @Override
  public void onStartup(ServletContext servletContext) {
    servletContext.addListener(new RequestContextListener());
  }
}
