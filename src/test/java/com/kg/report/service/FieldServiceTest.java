package com.kg.report.service;

import com.kg.report.model.enums.ActionCodeEnum;
import com.kg.report.model.enums.FieldTypeEnum;
import com.kg.report.model.vo.FieldCreatorVO;
import com.kg.report.model.vo.FieldEditorVO;
import com.kg.report.model.vo.FieldVO;
import com.kg.report.utils.exception.ServiceActionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class FieldServiceTest {

  @Autowired
  FieldService fieldService;

  @Test
  public void add() throws ServiceActionException {
    FieldCreatorVO creatorVO = new FieldCreatorVO();
    creatorVO.setName("最大客运量");
    creatorVO.setRequired(true);
    creatorVO.setType(FieldTypeEnum.Integers);
    int fieldId = fieldService.add(creatorVO);
    assertTrue(fieldId > 0);
    FieldVO fieldVO = fieldService.getDetail(fieldId);
    assertEquals(fieldVO.getId(), fieldId);
    assertEquals(fieldVO.getType(), creatorVO.getType());
    assertTrue(fieldVO.isRequired());
    try {
      fieldService.add(creatorVO);
    } catch (ServiceActionException ex) {
      assertEquals(ActionCodeEnum.FieldIsExist, ex.getActionCode());
    }
  }

  @Test
  public void update() throws ServiceActionException {
    FieldCreatorVO creatorVO = new FieldCreatorVO();
    creatorVO.setName("最大客运量");
    creatorVO.setRequired(true);
    creatorVO.setType(FieldTypeEnum.Integers);
    int fieldId = fieldService.add(creatorVO);
    assertTrue(fieldId > 0);
    creatorVO.setName("最大货运量");
    fieldService.add(creatorVO);
    FieldEditorVO editorVO = new FieldEditorVO();
    editorVO.setName("最大货运量");
    editorVO.setId(fieldId);
    editorVO.setRequired(false);
    editorVO.setType(FieldTypeEnum.Float);
    try {
      fieldService.update(editorVO);
    } catch (ServiceActionException ex) {
      assertEquals(ActionCodeEnum.FieldIsExist, ex.getActionCode());
    }
    editorVO.setName("客人投诉");
    fieldService.update(editorVO);
    FieldVO fieldVO = fieldService.getDetail(fieldId);
    assertFalse(fieldVO.isRequired());
    assertEquals(fieldVO.getType(), FieldTypeEnum.Float);
  }

  @Test
  public void delete() throws ServiceActionException {
    FieldCreatorVO creatorVO = new FieldCreatorVO();
    creatorVO.setName("最大客运量");
    creatorVO.setRequired(true);
    creatorVO.setType(FieldTypeEnum.Integers);
    int fieldId = fieldService.add(creatorVO);
    assertTrue(fieldId > 0);
    fieldService.delete(fieldId);
    try {
      FieldVO fieldVO = fieldService.getDetail(fieldId);
    } catch (ServiceActionException ex) {
      assertEquals(ActionCodeEnum.FieldNotExist, ex.getActionCode());
    }
  }
}