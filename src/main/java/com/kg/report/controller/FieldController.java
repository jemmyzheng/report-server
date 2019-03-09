package com.kg.report.controller;

import com.kg.report.CurrentUser;
import com.kg.report.model.enums.RoleEnum;
import com.kg.report.model.vo.*;
import com.kg.report.service.FieldService;
import com.kg.report.service.ReportService;
import com.kg.report.utils.ApiResult;
import com.kg.report.utils.DoAuth;
import com.kg.report.utils.exception.ServiceActionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class FieldController {
  @Autowired
  FieldService fieldService;

  @Autowired
  ReportService reportService;

  @Autowired
  CurrentUser currentUser;

  @GetMapping("/api/fields")
  public ApiResult<List<FieldVO>> getAll() {
    List<FieldVO> result = fieldService.queryAll();
    return ApiResult.hasDone(result);
  }

  @PostMapping("/api/fields")
  @DoAuth
  public ApiResult<Integer> add(@RequestBody @Valid FieldCreatorVO creatorVO) throws ServiceActionException {
    int result = fieldService.add(creatorVO);
    return ApiResult.hasDone(result);
  }

  @PutMapping("/api/fields/{id}")
  @DoAuth
  public ApiResult edit(@PathVariable("id") int id, @RequestBody @Valid FieldEditorVO editorVO) throws ServiceActionException {
    fieldService.update(editorVO);
    return ApiResult.hasDone();
  }

  @GetMapping("/api/current/fields")
  public ApiResult getCurrentUserFields() throws ServiceActionException {
    if (currentUser.getRole().equals(RoleEnum.Manager)) {
      return getAll();
    }
    List<ReportVO> reports = reportService.getByDept(currentUser.getDeptId());
    Map<Integer, FieldMinVO> results = new HashMap<>();
    for (ReportVO report : reports) {
      List<FieldMinVO> fields = reportService.getFields(report.getId());
      fields.forEach(f -> results.put(f.getId(), f));
    }
    return ApiResult.hasDone(results.values());
  }
}
