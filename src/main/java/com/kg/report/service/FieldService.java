package com.kg.report.service;

import com.kg.report.model.enums.ActionCodeEnum;
import com.kg.report.model.enums.FieldTypeEnum;
import com.kg.report.model.po.FieldPO;
import com.kg.report.model.vo.FieldCreatorVO;
import com.kg.report.model.vo.FieldEditorVO;
import com.kg.report.model.vo.FieldMinVO;
import com.kg.report.model.vo.FieldVO;
import com.kg.report.service.mapper.FieldMapper;
import com.kg.report.utils.Converter;
import com.kg.report.utils.exception.ServiceActionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FieldService {
  @Autowired
  FieldMapper fieldMapper;

  public int add(FieldCreatorVO creatorVO) throws ServiceActionException {
    boolean isExist = fieldMapper.nameExist(creatorVO.getName()) != null;
    if (isExist) throw new ServiceActionException(ActionCodeEnum.FieldIsExist);
    FieldPO po = Converter.copyProperties(creatorVO, FieldPO.class, "required", "type");
    int res = fieldMapper.insert(po);
    if (res > 0) return po.getId();
    throw new ServiceActionException(ActionCodeEnum.Failed);
  }

  public FieldVO getDetail(int id) throws ServiceActionException {
    FieldPO po = fieldMapper.selectById(id);
    if (po == null) throw new ServiceActionException(ActionCodeEnum.FieldNotExist);
    return Converter.copyProperties(po, FieldVO.class, "required", "type");
  }

  public void update(FieldEditorVO editorVO) throws ServiceActionException {
    FieldPO origin = fieldMapper.selectById(editorVO.getId());
    if (origin == null) throw new ServiceActionException(ActionCodeEnum.FieldNotExist);
    if (!origin.getName().equals(editorVO.getName())) {
      boolean isExist = fieldMapper.nameExist(editorVO.getName()) != null;
      if (isExist) throw new ServiceActionException(ActionCodeEnum.FieldIsExist);
    }
    short required = editorVO.isRequired() ? (short)1 : (short)0;
    int res = fieldMapper.update(editorVO.getId(), editorVO.getName(), editorVO.getType().getValue(), required);
    if (res <= 0) throw new ServiceActionException(ActionCodeEnum.Failed);
  }

  /**
   * 慎重使用，会关联删除报表中已填写的数据，物理删除
   * @param id
   */
  public void delete(int id) throws ServiceActionException {
    FieldPO origin = fieldMapper.selectById(id);
    if (origin == null) throw new ServiceActionException(ActionCodeEnum.FieldNotExist);
    int res = fieldMapper.deleteById(id);
    if (res <= 0) throw new ServiceActionException(ActionCodeEnum.Failed);
  }

  public List<FieldVO> queryAll() {
    List<FieldPO> pos = fieldMapper.selectAll();
    return Converter.copyList(pos, FieldVO.class, "type", "required");
  }

  public List<FieldMinVO> queryMinAll() {
    List<FieldPO> pos = fieldMapper.selectAll();
    return Converter.copyList(pos, FieldMinVO.class, "type", "required");
  }

}
