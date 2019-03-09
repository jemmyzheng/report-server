package com.kg.report.service.mapper;

import com.kg.report.model.po.ReportFieldListPO;
import com.kg.report.model.po.ReportPO;import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReportMapper {
    int deleteById(Integer id);

    int insert(ReportPO record);

    ReportPO selectById(Integer id);

    List<ReportPO> selectByDeptId(int deptId);

    List<ReportPO> selectAll();

    List<ReportFieldListPO> selectReportWithFieldId(@Param("deptId") int deptId);

    int updateBy(ReportPO record);
}