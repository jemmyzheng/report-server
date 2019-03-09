package com.kg.report.service.mapper;

import com.kg.report.model.po.FieldWritePO;
import com.kg.report.model.po.ReportValueGroupPO;
import com.kg.report.model.po.ReportValuePO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ReportValueMapper {
    int deleteByPrimaryKey(@Param("reportId") Integer reportId, @Param("fieldId") Integer fieldId, @Param("airportId") Integer airportId);

    int deleteByReportId(@Param("reportId") Integer reportId);

    int deleteByReportIdAndFieldId(@Param("reportId") Integer reportId, @Param("fieldId") Integer fieldId);

    int insert(ReportValuePO record);

    ReportValuePO selectByPrimaryKey(@Param("reportId") Integer reportId, @Param("fieldId") Integer fieldId, @Param("airportId") Integer airportId);

    List<ReportValuePO> selectBy(@Param("reportId") Integer reportId,
                                 @Param("airportId") Integer airportId,
                                 @Param("addTimeStart") Date addTimeStart,
                                 @Param("addTimeEnd") Date addTimeEnd
    );

    List<ReportValueGroupPO> groupSelect(
        @Param("reportId") int[] reportId,
        @Param("airportId") Integer airportId,
        @Param("addTimeStart") Date addTimeStart,
        @Param("addTimeEnd") Date addTimeEnd
    );

    List<FieldWritePO> queryForExcel(
        @Param("reportId") int[] reportId,
        @Param("airportId") Integer airportId,
        @Param("addTimeStart") Date addTimeStart,
        @Param("addTimeEnd") Date addTimeEnd
    );

    ReportValuePO selectById(int id);

    int updateByPrimaryKey(ReportValuePO record);
}