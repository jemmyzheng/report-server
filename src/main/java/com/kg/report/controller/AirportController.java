package com.kg.report.controller;

import com.kg.report.model.vo.*;
import com.kg.report.service.AirportService;
import com.kg.report.utils.ApiResult;
import com.kg.report.utils.DoAuth;
import com.kg.report.utils.exception.ServiceActionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AirportController {
  @Autowired
  AirportService airportService;

  @GetMapping("/api/airports")
  public ApiResult<List<AirportVO>> getAll() throws ServiceActionException {
    List<AirportVO> result = airportService.queryAll();
    return ApiResult.hasDone(result);
  }

  @PostMapping("/api/airports")
  @DoAuth
  public ApiResult<Integer> add(@RequestBody @Valid AirportCreatorVO creatorVO) throws ServiceActionException {
    int result = airportService.add(creatorVO);
    return ApiResult.hasDone(result);
  }

  @PutMapping("/api/airports/{id}")
  @DoAuth
  public ApiResult edit(@PathVariable("id") int id, @RequestBody @Valid AirportVO editorVO) throws ServiceActionException {
    airportService.update(editorVO);
    return ApiResult.hasDone();
  }
}
