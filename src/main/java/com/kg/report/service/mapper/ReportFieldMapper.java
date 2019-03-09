package com.kg.report.service.mapper;

import com.kg.report.model.po.FieldPO;
import com.kg.report.model.po.ReportFieldPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReportFieldMapper {
    int deleteByPrimaryKey(@Param("reportId") Integer reportId, @Param("fieldId") Integer fieldId);

    int insert(ReportFieldPO record);

    int batchAddReportField(List<ReportFieldPO> record);

    int deleteByFieldId(@Param("fieldId") Integer fieldId);

    int deleteByReportId(@Param("reportId") Integer reportId);

    List<FieldPO> selectFieldBy(@Param("reportId") Integer reportId);
}