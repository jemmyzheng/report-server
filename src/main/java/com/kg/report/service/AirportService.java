package com.kg.report.service;

import com.kg.report.model.enums.ActionCodeEnum;
import com.kg.report.model.po.AirportPO;
import com.kg.report.model.vo.AirportCreatorVO;
import com.kg.report.model.vo.AirportVO;
import com.kg.report.service.mapper.AirportMapper;
import com.kg.report.utils.Converter;
import com.kg.report.utils.exception.ServiceActionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportService {
  @Autowired
  AirportMapper airportMapper;

  public int add(AirportCreatorVO creatorVO) throws ServiceActionException {
    boolean isExist = airportMapper.nameExist(creatorVO.getName()) != null;
    if (isExist) throw new ServiceActionException(ActionCodeEnum.AirportIsExist);
    AirportPO po = Converter.copyProperties(creatorVO, AirportPO.class);
    int res = airportMapper.insert(po);
    if (res > 0) return po.getId();
    throw new ServiceActionException(ActionCodeEnum.Failed);
  }

  public List<AirportVO> queryAll() throws ServiceActionException {
    List<AirportPO> pos = airportMapper.selectAll();
    return Converter.copyList(pos, AirportVO.class);
  }

  public void update(AirportVO vo) throws ServiceActionException {
    AirportPO origin = airportMapper.selectByPrimaryKey(vo.getId());
    if (origin == null) throw new ServiceActionException(ActionCodeEnum.AirportNotExist);
    if (!origin.getName().equals(vo.getName())) {
      boolean isExist = airportMapper.nameExist(vo.getName()) != null;
      if (isExist) throw new ServiceActionException(ActionCodeEnum.AirportIsExist);
    }
    int res = airportMapper.updateByPrimaryKey(vo.getId(), vo.getName());
    if (res <= 0) throw new ServiceActionException(ActionCodeEnum.Failed);
  }
}
