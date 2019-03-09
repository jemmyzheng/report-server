package com.kg.report.service;

import com.kg.report.model.enums.ActionCodeEnum;
import com.kg.report.model.vo.DeptCreatorVO;
import com.kg.report.model.vo.DeptEditorVO;
import com.kg.report.model.vo.DeptVO;
import com.kg.report.utils.exception.ServiceActionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class DeptServiceTest {

  @Autowired
  DeptService deptService;

  @Test
  public void add() throws ServiceActionException {
    DeptCreatorVO creatorVO = new DeptCreatorVO();
    creatorVO.setName("财务");
    int id = deptService.add(creatorVO);
    assertTrue(id > 0);
    try {
      deptService.add(creatorVO);
    } catch (ServiceActionException ex) {
      assertEquals(ActionCodeEnum.DeptIsExist, ex.getActionCode());
    }
  }

  @Test
  public void edit() throws ServiceActionException {
    DeptCreatorVO creatorVO = new DeptCreatorVO();
    creatorVO.setName("财务");
    int dept1Id = deptService.add(creatorVO);
    creatorVO.setName("货运");
    deptService.add(creatorVO);
    DeptEditorVO editorVO = new DeptEditorVO();
    editorVO.setId(dept1Id);
    editorVO.setName("货运");
    try {
      deptService.edit(editorVO);
    } catch (ServiceActionException ex) {
      assertEquals(ActionCodeEnum.DeptIsExist, ex.getActionCode());
    }
    editorVO.setName("贵宾");
    deptService.edit(editorVO);
  }

  @Test
  public void getAll() throws ServiceActionException {
    DeptCreatorVO creatorVO = new DeptCreatorVO();
    creatorVO.setName("财务");
    deptService.add(creatorVO);
    List<DeptVO> list = deptService.getAll();
    assertTrue(list.size() > 0);
  }
}