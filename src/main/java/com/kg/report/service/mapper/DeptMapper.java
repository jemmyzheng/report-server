package com.kg.report.service.mapper;

import com.kg.report.model.po.DeptPO;

import java.util.List;

public interface DeptMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeptPO record);

    DeptPO selectById(Integer id);

    String nameExist(String name);

    List<DeptPO> selectAll();

    int update(int id, String name);
}