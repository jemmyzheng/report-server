package com.kg.report.controller;

import com.kg.report.utils.ApiResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
  @GetMapping("/")
  public ApiResult index() {
    return ApiResult.hasDone("服务已启动");
  }
}
