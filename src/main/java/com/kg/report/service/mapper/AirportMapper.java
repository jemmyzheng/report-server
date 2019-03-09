package com.kg.report.service.mapper;

import com.kg.report.model.po.AirportPO;

import java.util.List;

public interface AirportMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AirportPO record);

    String nameExist(String name);

    AirportPO selectByPrimaryKey(Integer id);

    List<AirportPO> selectAll();

    int updateByPrimaryKey(int id, String name);
}