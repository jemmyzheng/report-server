package com.kg.report.service;

import com.kg.report.model.enums.ActionCodeEnum;
import com.kg.report.model.enums.FieldTypeEnum;
import com.kg.report.model.enums.ReportTypeEnum;
import com.kg.report.model.enums.ValueStateEnum;
import com.kg.report.model.po.ReportValueGroupPO;
import com.kg.report.model.vo.*;
import com.kg.report.utils.DateUtils;
import com.kg.report.utils.PagesVO;
import com.kg.report.utils.Pagination;
import com.kg.report.utils.exception.ServiceActionException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ReportValueServiceTest {

  @Autowired
  private ReportValueService reportValueService;
  @Autowired
  private ReportService reportService;
  @Autowired
  private FieldService fieldService;
  int reportId;
  int fieldId1;
  int fieldId2;
  @Before
  public void setUp() throws ServiceActionException {
    FieldCreatorVO creatorVO = new FieldCreatorVO();
    creatorVO.setName("最大客运量");
    creatorVO.setRequired(true);
    creatorVO.setType(FieldTypeEnum.Integers);
    fieldId1 = fieldService.add(creatorVO);
    creatorVO.setName("客户投诉");
    fieldId2 = fieldService.add(creatorVO);

    ReportCreatorVO reportCreatorVO = new ReportCreatorVO();
    reportCreatorVO.setName("财务报表");
    reportCreatorVO.setDeadline("00-01-00-08-30");// 每周一上午8点半
    reportCreatorVO.setDeptId(1);
    reportCreatorVO.setType(ReportTypeEnum.Weekly);
    reportCreatorVO.setFields(new ArrayList<>());
    reportCreatorVO.getFields().add(fieldId1);
    reportCreatorVO.getFields().add(fieldId2);
    reportId = reportService.add(reportCreatorVO, 1);
  }

  @Test
  public void addValue() throws ServiceActionException {
    ReportValueEditVO editVO = new ReportValueEditVO();
    editVO.setReportId(reportId);
    editVO.setAirportId(1);
    editVO.setValues(new ArrayList<>());
    ValueItemVO itemVO = new ValueItemVO();
    itemVO.setFieldId(fieldId1);
    itemVO.setValue("123");
    editVO.getValues().add(itemVO);

    ValueItemVO itemVO2 = new ValueItemVO();
    itemVO2.setFieldId(fieldId2);
    itemVO2.setValue("123");
    editVO.getValues().add(itemVO2);
    reportValueService.addValue(editVO, 1);
    try {
      reportValueService.addValue(editVO, 1);
    } catch (ServiceActionException ex) {
      assertEquals(ActionCodeEnum.ReportValueIsInput, ex.getActionCode());
    }
    Pagination pagination = new Pagination();
    pagination.setCurrent(1);
    pagination.setPageSize(20);
    pagination.setPagination(true);
    DateUtils dateUtils = new DateUtils(ReportTypeEnum.Weekly, "00-01-00-08-30");
    PagesVO<ReportValueVO> pages = reportValueService.queryValues(reportId, 1, pagination, dateUtils.getQueryRange());
    assertTrue(pages.getTotal() >= 2);
    pages.getDataList().forEach(reportValueVO -> {
      if (reportValueVO.getFieldId() == fieldId1) {
        assertEquals(reportValueVO.getState(), ValueStateEnum.Timeout);
      }
    });
    int[] reportIds = {reportId};
    PagesVO<ReportValueGroupVO> pages2 = reportValueService.queryGroupValues(reportIds, 1, pagination, dateUtils.getQueryRange());
    assertTrue(pages2.getTotal() >= 1);
  }

  @Test
  public void editValue() {
    // TODO
  }
}