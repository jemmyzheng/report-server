package com.kg.report.service.mapper;

import com.kg.report.model.po.FieldPO;

import java.util.List;

public interface FieldMapper {
    int deleteById(Integer id);

    int insert(FieldPO record);

    String nameExist(String name);

    FieldPO selectById(Integer id);

    List<FieldPO> selectAll();

    int update(int id, String name, short type, short required);
}