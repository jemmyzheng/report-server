package com.kg.report.controller;

import com.kg.report.CurrentUser;
import com.kg.report.model.enums.RoleEnum;
import com.kg.report.model.vo.*;
import com.kg.report.service.FieldService;
import com.kg.report.service.ReportService;
import com.kg.report.service.ReportValueService;
import com.kg.report.utils.*;
import com.kg.report.utils.exception.ServiceActionException;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
public class ReportController {
  @Autowired
  ReportService reportService;

  @Autowired
  ReportValueService reportValueService;

  @Autowired
  CurrentUser currentUser;

  @InitBinder
  protected void initBinder(ServletRequestDataBinder binder) {
    binder.registerCustomEditor(Date.class, new DateEditor());
  }

  @GetMapping("/api/reports")
  public ApiResult<List<ReportVO>> getAll(int deptId) {
    List<ReportVO> result;
    if (deptId > 0) {
      result = reportService.getByDept(deptId);
    } else {
      result = reportService.getAll();
    }
    return ApiResult.hasDone(result);
  }

  @GetMapping("/api/current/reports")
  public ApiResult<List<ReportListVO>> getCurrentUserReports() throws ServiceActionException {
    List<ReportListVO> results = reportService.getReportWithField(currentUser.getRole().equals(RoleEnum.Manager) ? 0 : currentUser.getDeptId());
    return ApiResult.hasDone(results);
  }

  @PostMapping("/api/reports")
  @DoAuth
  public ApiResult<Integer> add(@RequestBody @Valid ReportCreatorVO creatorVO) throws ServiceActionException {
    int result = reportService.add(creatorVO, currentUser.getUserId());
    return ApiResult.hasDone(result);
  }

  @GetMapping("/api/reports/{id}")
  public ApiResult<ReportDetailVO> getDetail(@PathVariable("id") int id) throws ServiceActionException {
    return ApiResult.hasDone(reportService.getDetailById(id));
  }

  @PutMapping("/api/reports/{id}")
  @DoAuth
  public ApiResult edit(@PathVariable("id") int id, @RequestBody @Valid ReportEditorVO editorVO) throws ServiceActionException {
    reportService.update(editorVO, currentUser.getUserId());
    return ApiResult.hasDone();
  }

  @DeleteMapping("/api/reports/{id}")
  @DoAuth
  public ApiResult delete(@PathVariable("id") int id) throws ServiceActionException {
    reportService.delete(id);
    return ApiResult.hasDone();
  }

  @PutMapping("/api/reports/{id}/fields")
  @DoAuth
  public ApiResult addFields(@PathVariable("id") int id, @RequestBody @Valid ReportFieldVO reportFieldVO) throws ServiceActionException {
    reportService.setFields(reportFieldVO);
    return ApiResult.hasDone();
  }

  @DeleteMapping("/api/reports/{id}/fields")
  @DoAuth
  public ApiResult rmField(@PathVariable("id") int id, @RequestBody @Valid ReportRemoveFieldVO removeFieldVO) throws ServiceActionException {
    reportService.removeFieldFrom(removeFieldVO);
    return ApiResult.hasDone();
  }

  @GetMapping("/api/reports/{id}/fields")
  public ApiResult<List<FieldMinVO>> getFields(@PathVariable("id") int id) throws ServiceActionException {
    List<FieldMinVO> result = reportService.getFields(id);
    return ApiResult.hasDone(result);
  }

  @PostMapping("/api/reports/{id}/values")
  public ApiResult inputReportValue(@PathVariable("id") int id, @RequestBody @Valid ReportValueEditVO editVO) throws ServiceActionException {
    reportValueService.addValue(editVO, currentUser.getUserId());
    return ApiResult.hasDone();
  }

  /**
   * 目前仅仅支持修改已录入的数据，如果报表新增字段了，不支持补录
   * @param id
   * @param editVO
   * @return
   * @throws ServiceActionException
   */
  @PutMapping("/api/reports/{id}/values")
  public ApiResult editReportValue(@PathVariable("id") int id, @RequestBody @Valid ReportValueEditVO editVO) throws ServiceActionException {
    reportValueService.editValue(editVO, currentUser.getUserId());
    return ApiResult.hasDone();
  }

  @GetMapping("/api/reports/values")
  public ApiResult<PagesVO<ReportValueGroupVO>> getReportValue(ReportDataSearcher searcher,  Pagination pagination) throws ServiceActionException {
    Date[] dates = {searcher.getBeginDate(), searcher.getEndDate()};
    if (searcher.getEndDate() != null) {
      Calendar calendars = Calendar.getInstance();
      calendars.setTime(dates[1]);
      calendars.set(Calendar.HOUR_OF_DAY, 23);
      dates[1] = calendars.getTime();
    }
    PagesVO<ReportValueGroupVO> result = reportValueService.queryGroupValues(
        searcher.getReportId(), searcher.getAirportId(), pagination, dates
    );
    return ApiResult.hasDone(result);
  }

  @GetMapping("/api/reports/export")
  public ResponseEntity<byte[]> exportExcel(ReportDataSearcher searcher) throws ServiceActionException, IOException {
    Date[] dates = {searcher.getBeginDate(), searcher.getEndDate()};
    if (searcher.getEndDate() != null) {
      Calendar calendars = Calendar.getInstance();
      calendars.setTime(dates[1]);
      calendars.set(Calendar.HOUR_OF_DAY, 23);
      dates[1] = calendars.getTime();
    }
    byte[] file = reportValueService.export2Excel(searcher.getReportId(), searcher.getAirportId(), dates);
    HttpHeaders headers = new HttpHeaders();
    String downloadFilename = "湖南空港实业股份有限公司报表.xlsx";
    if (dates.length == 1 || DateUtils.isSameDay(searcher.getBeginDate(), searcher.getEndDate())) {
      downloadFilename = com.kg.report.utils.DateUtils.formatDateHuman(dates[0]) + downloadFilename;
    } else {
      downloadFilename = com.kg.report.utils.DateUtils.formatDateHuman(dates[0]) + "-" + com.kg.report.utils.DateUtils.formatDateHuman(dates[1]) + downloadFilename;
    }
    try {
      downloadFilename = URLEncoder.encode(downloadFilename, "utf-8");
    } catch (UnsupportedEncodingException ex) {
      // no need do anything
    }
    headers.setContentDispositionFormData("attachment", downloadFilename);
    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    return new ResponseEntity<byte[]>(file, headers, HttpStatus.OK);
  }

}
