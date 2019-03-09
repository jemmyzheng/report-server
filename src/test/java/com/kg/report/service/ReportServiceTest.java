package com.kg.report.service;

import com.kg.report.model.enums.ActionCodeEnum;
import com.kg.report.model.enums.FieldTypeEnum;
import com.kg.report.model.enums.ReportTypeEnum;
import com.kg.report.model.vo.*;
import com.kg.report.utils.exception.ServiceActionException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ReportServiceTest {

  @Autowired
  ReportService reportService;

  @Autowired
  FieldService fieldService;

  private int fieldId1 = 0;
  private int fieldId2 = 0;

  @Before
  public void setUp() throws ServiceActionException {
    FieldCreatorVO creatorVO = new FieldCreatorVO();
    creatorVO.setName("最大客运量");
    creatorVO.setRequired(true);
    creatorVO.setType(FieldTypeEnum.Integers);
    fieldId1 = fieldService.add(creatorVO);
    creatorVO.setName("客户投诉");
    fieldId2 = fieldService.add(creatorVO);
  }

  @Test
  public void add() throws ServiceActionException {
    ReportCreatorVO creatorVO = new ReportCreatorVO();
    creatorVO.setName("财务报表");
    creatorVO.setDeadline("00-01-00-08-30");// 每周一上午8点半
    creatorVO.setDeptId(1);
    creatorVO.setType(ReportTypeEnum.Weekly);
    creatorVO.setFields(new ArrayList<>());
    creatorVO.getFields().add(fieldId1);
    creatorVO.getFields().add(fieldId2);
    int reportId = reportService.add(creatorVO, 1);
    assertTrue(reportId > 0);
    ReportVO reportVO = reportService.getById(reportId);
    assertEquals(reportVO.getDeadline(), creatorVO.getDeadline());
    assertEquals(reportVO.getModifyUserId(), 1);
    assertEquals(reportVO.getType(), ReportTypeEnum.Weekly);
    List<FieldMinVO> fields = reportService.getFields(reportId);
    assertEquals(2, fields.size());
  }

  @Test
  public void delete() throws ServiceActionException {
    ReportCreatorVO creatorVO = new ReportCreatorVO();
    creatorVO.setName("财务报表");
    creatorVO.setDeadline("00-01-00-08-30");// 每周一上午8点半
    creatorVO.setDeptId(1);
    creatorVO.setType(ReportTypeEnum.Weekly);
    creatorVO.setFields(new ArrayList<>());
    creatorVO.getFields().add(fieldId1);
    creatorVO.getFields().add(fieldId2);
    int reportId = reportService.add(creatorVO, 1);
    assertTrue(reportId > 0);
    reportService.delete(reportId);
    try {
      reportService.getById(reportId);
    } catch (ServiceActionException ex) {
      assertEquals(ActionCodeEnum.ReportNotExist, ex.getActionCode());
    }
  }

  @Test
  public void getByDept() throws ServiceActionException {
    ReportCreatorVO creatorVO = new ReportCreatorVO();
    creatorVO.setName("财务报表");
    creatorVO.setDeadline("00-00-06-08-30");// 每月6日上午8点半
    creatorVO.setDeptId(20);
    creatorVO.setType(ReportTypeEnum.Monthly);
    reportService.add(creatorVO, 1);
    creatorVO.setName("贵宾报表");
    reportService.add(creatorVO, 1);
    List<ReportVO> res = reportService.getByDept(20);
    assertTrue(res.size() >= 2);
  }

  @Test
  public void getAll() throws ServiceActionException {
    ReportCreatorVO creatorVO = new ReportCreatorVO();
    creatorVO.setName("财务报表");
    creatorVO.setDeadline("01-00-30-08-30");// 每年1月30日上午8点半
    creatorVO.setDeptId(20);
    creatorVO.setType(ReportTypeEnum.Annually);
    reportService.add(creatorVO, 1);
    creatorVO.setName("贵宾报表");
    reportService.add(creatorVO, 1);
    List<ReportVO> res = reportService.getAll();
    assertTrue(res.size() >= 2);
  }

  @Test
  public void update() throws ServiceActionException {
    ReportCreatorVO creatorVO = new ReportCreatorVO();
    creatorVO.setName("财务报表");
    creatorVO.setDeadline("00-00-00-08-30");// 每日上午8点半
    creatorVO.setDeptId(20);
    creatorVO.setType(ReportTypeEnum.Daily);
    int reportId = reportService.add(creatorVO, 1);
    ReportEditorVO editorVO = new ReportEditorVO();
    editorVO.setId(reportId);
    editorVO.setName("其它报表");
    editorVO.setType(ReportTypeEnum.Weekly);
    editorVO.setDeadline("00-02-00-09-30"); // 每周二上午9点半
    reportService.update(editorVO, 1);
    ReportVO reportVO = reportService.getById(editorVO.getId());
    assertEquals(reportVO.getDeadline(), editorVO.getDeadline());
    assertEquals(reportVO.getType(), editorVO.getType());
  }
}