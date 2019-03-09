package com.kg.report.controller;

import com.kg.report.model.vo.DeptCreatorVO;
import com.kg.report.model.vo.DeptEditorVO;
import com.kg.report.model.vo.DeptVO;
import com.kg.report.service.DeptService;
import com.kg.report.utils.ApiResult;
import com.kg.report.utils.DoAuth;
import com.kg.report.utils.exception.ServiceActionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class DeptController {
  @Autowired
  DeptService deptService;

  @GetMapping("/api/depts")
  public ApiResult<List<DeptVO>> getAllDepts() {
    List<DeptVO> result = deptService.getAll();
    return ApiResult.hasDone(result);
  }

  @PostMapping("/api/depts")
  @DoAuth
  public ApiResult<Integer> addDept(@RequestBody @Valid DeptCreatorVO creatorVO) throws ServiceActionException {
    int result = deptService.add(creatorVO);
    return ApiResult.hasDone(result);
  }

  @PutMapping("/api/depts/{id}")
  @DoAuth
  public ApiResult editDept(@PathVariable("id") int id, @RequestBody @Valid DeptEditorVO editorVO) throws ServiceActionException {
    deptService.edit(editorVO);
    return ApiResult.hasDone();
  }
}
