package com.kg.report.service;

import com.kg.report.model.enums.ActionCodeEnum;
import com.kg.report.model.po.DeptPO;
import com.kg.report.model.vo.DeptCreatorVO;
import com.kg.report.model.vo.DeptEditorVO;
import com.kg.report.model.vo.DeptVO;
import com.kg.report.service.mapper.DeptMapper;
import com.kg.report.utils.Converter;
import com.kg.report.utils.exception.ServiceActionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptService {
  @Autowired
  DeptMapper deptMapper;

  public int add(DeptCreatorVO creatorVO) throws ServiceActionException {
    boolean isExist = deptMapper.nameExist(creatorVO.getName()) != null;
    if (isExist) throw new ServiceActionException(ActionCodeEnum.DeptIsExist);
    DeptPO po = new DeptPO();
    po.setName(creatorVO.getName());
    int res = deptMapper.insert(po);
    if (res > 0) return po.getId();
    throw new ServiceActionException(ActionCodeEnum.Failed);
  }

  public void edit(DeptEditorVO editorVO) throws ServiceActionException {
    DeptPO origin = deptMapper.selectById(editorVO.getId());
    if (origin == null) throw new ServiceActionException(ActionCodeEnum.DeptNotExist);
    if (!editorVO.getName().equals(origin.getName())) {
      boolean isExist = deptMapper.nameExist(editorVO.getName()) != null;
      if (isExist) throw new ServiceActionException(ActionCodeEnum.DeptIsExist);
    }
    int res = deptMapper.update(editorVO.getId(), editorVO.getName());
    if (res <= 0) throw new ServiceActionException(ActionCodeEnum.Failed);
  }

  public DeptVO getById(int id) throws ServiceActionException {
    DeptPO po = deptMapper.selectById(id);
    if (po == null) throw new ServiceActionException(ActionCodeEnum.DeptNotExist);
    return Converter.copyProperties(po, DeptVO.class);
  }

  public List<DeptVO> getAll() {
    List<DeptPO> pos = deptMapper.selectAll();
    return Converter.copyList(pos, DeptVO.class);
  }
}
