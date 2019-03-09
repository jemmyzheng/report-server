package com.kg.report.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kg.report.model.enums.ActionCodeEnum;
import com.kg.report.model.enums.ValueStateEnum;
import com.kg.report.model.po.FieldWritePO;
import com.kg.report.model.po.ReportValueGroupPO;
import com.kg.report.model.po.ReportValuePO;
import com.kg.report.model.vo.*;
import com.kg.report.service.mapper.ReportValueMapper;
import com.kg.report.utils.DateUtils;
import com.kg.report.utils.ExcelUtils;
import com.kg.report.utils.PagesVO;
import com.kg.report.utils.Pagination;
import com.kg.report.utils.exception.ServiceActionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

@Service
public class ReportValueService {
  @Autowired
  ReportValueMapper reportValueMapper;

  @Autowired
  ReportService reportService;

  @Autowired
  FieldService fieldService;

  @Resource(name = "transactionManager")
  private PlatformTransactionManager platformTransactionManager;

  protected Logger logger = LoggerFactory.getLogger(this.getClass());

  public void addValue(ReportValueEditVO editVO, int userId) throws ServiceActionException {
    ReportVO report = reportService.getById(editVO.getReportId());
    DateUtils dateUtils;
    if (editVO.isMakeUp()) {
      dateUtils = new DateUtils(report.getType(), report.getDeadline(), editVO.getMakeUpDate());
    } else {
      dateUtils = new DateUtils(report.getType(), report.getDeadline());
    }
    List<ReportValuePO> pos = reportValueMapper.selectBy(editVO.getReportId(), editVO.getAirportId(), dateUtils.getQueryRange()[0], dateUtils.getQueryRange()[1]);
    if (pos.size() > 0) {
      throw new ServiceActionException(ActionCodeEnum.ReportValueIsInput);
    }
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
    TransactionStatus status = platformTransactionManager.getTransaction(def);

    try {
      for (ValueItemVO item : editVO.getValues()) {
        ReportValuePO valuePO = new ReportValuePO();
        valuePO.setFieldId(item.getFieldId());
        valuePO.setValue(item.getValue());
        valuePO.setAirportId(editVO.getAirportId());
        valuePO.setReportId(editVO.getReportId());
        valuePO.setModifyUserId(userId);
        valuePO.setModifyValue("0");
        if (editVO.isMakeUp()) {
          valuePO.setState(ValueStateEnum.Timeout.getValue());
          valuePO.setAddTime(editVO.getMakeUpDate());
          valuePO.setAddDay(DateUtils.formatDate(editVO.getMakeUpDate()));
        } else {
          valuePO.setState(dateUtils.isOverDeadLine() ? ValueStateEnum.Timeout.getValue() : ValueStateEnum.Normal.getValue());
          valuePO.setAddTime(new Date());
          valuePO.setAddDay(DateUtils.formatDate(valuePO.getAddTime()));
        }
        int res = reportValueMapper.insert(valuePO);
        if (res <= 0) {
          platformTransactionManager.rollback(status);
          throw new ServiceActionException(ActionCodeEnum.Failed);
        }
      }
      platformTransactionManager.commit(status);
    } catch (Exception ex) {
      logger.error("添加报表数据", ex);
      platformTransactionManager.rollback(status);
      throw new ServiceActionException(ActionCodeEnum.Failed);
    }
  }

  public void editValue(ReportValueEditVO editVO, int userId) throws ServiceActionException {
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
    TransactionStatus status = platformTransactionManager.getTransaction(def);

    try {
      for (ValueItemVO item : editVO.getValues()) {
        if (reportValueMapper.selectById(item.getId()) == null) {
          throw new ServiceActionException(ActionCodeEnum.ReportValueNotInput);
        }
        ReportValuePO valuePO = new ReportValuePO();
        valuePO.setFieldId(item.getFieldId());
        valuePO.setValue(item.getValue());
        valuePO.setAirportId(editVO.getAirportId());
        valuePO.setReportId(editVO.getReportId());
        valuePO.setState((short)2);
        valuePO.setModifyUserId(userId);
        valuePO.setModifyValue(item.getValue());
        valuePO.setId(item.getId());
        int res = reportValueMapper.updateByPrimaryKey(valuePO);
        if (res <= 0) {
          platformTransactionManager.rollback(status);
          throw new ServiceActionException(ActionCodeEnum.Failed);
        }
      }
      platformTransactionManager.commit(status);
    } catch (Exception ex) {
      logger.error("更新报表数据", ex);
      platformTransactionManager.rollback(status);
      throw new ServiceActionException(ActionCodeEnum.Failed);
    }
  }

  public PagesVO<ReportValueVO> queryValues(int reportId, int airportId, Pagination pagination, Date[] dateRange) throws ServiceActionException {
    if (pagination != null && pagination.isPagination()) {
      PageHelper.startPage(pagination.getCurrent(), pagination.getPageSize());
    }
    List<FieldMinVO> fields = fieldService.queryMinAll();

    Map<Integer, FieldMinVO> map = new HashMap<>();
    fields.forEach(fieldMinVO -> map.put(fieldMinVO.getId(), fieldMinVO));
    List<ReportValuePO> reportValuePOS = reportValueMapper.selectBy(reportId, airportId, dateRange[0], dateRange[1]);
    PagesVO<ReportValueVO> result = new PagesVO<>();
    if (reportValuePOS instanceof Page) {
      Page<ReportValuePO> page = (Page) reportValuePOS;
      result.setCurrent(page.getPageNum());
      result.setPages(page.getPages());
      result.setPageSize(page.getPageSize());
      result.setTotal(page.getTotal());
    } else {
      result.setCurrent(1);
      result.setPages(1);
      result.setPageSize(reportValuePOS.size());
      result.setTotal(reportValuePOS.size());
    }
    List<ReportValueVO> dataList = new ArrayList<>();
    for (ReportValuePO po : reportValuePOS) {
      FieldMinVO field = map.get(po.getFieldId());
      ReportValueVO valueVO = null;
      switch (field.getType()) {
        case Integers:
          valueVO = new ReportValueVO(Integer.valueOf(po.getValue()), Integer.valueOf(po.getModifyValue()), field.getName());
          break;
        case Float:
          valueVO = new ReportValueVO(Double.valueOf(po.getValue()), Double.valueOf(po.getModifyValue()), field.getName());
          break;
        case String:
        case Unknown:
          valueVO = new ReportValueVO(po.getValue(), po.getModifyValue(), field.getName());
          break;
      }
      valueVO.setState(ValueStateEnum.valueOf(po.getState()));
      BeanUtils.copyProperties(po, valueVO, "value", "modifyValue", "state");
      dataList.add(valueVO);
    }
    result.setDataList(dataList);
    return result;
  }

  public PagesVO<ReportValueGroupVO> queryGroupValues(int[] reportId, int airportId, Pagination pagination, Date[] dateRange) throws ServiceActionException {
    if (pagination != null && pagination.isPagination()) {
      PageHelper.startPage(pagination.getCurrent(), pagination.getPageSize());
    }
    Map<Integer, FieldMinVO> fieldMap = new HashMap<>();
    if (reportId == null) {
      reportId = new int[]{};
    }
    fieldService.queryMinAll().forEach(fieldMinVO -> fieldMap.put(fieldMinVO.getId(), fieldMinVO));

    List<ReportValueGroupPO> valuePOS = reportValueMapper.groupSelect(reportId, airportId, dateRange[0], dateRange[1]);

    PagesVO<ReportValueGroupVO> result = new PagesVO<>();
    if (valuePOS instanceof Page) {
      Page<ReportValueGroupVO> page = (Page) valuePOS;
      result.setCurrent(page.getPageNum());
      result.setPages(page.getPages());
      result.setPageSize(page.getPageSize());
      result.setTotal(page.getTotal());
    } else {
      result.setCurrent(1);
      result.setPages(1);
      result.setPageSize(valuePOS.size());
      result.setTotal(valuePOS.size());
    }

    List<ReportValueGroupVO> dataList = new ArrayList<>();

    for (ReportValueGroupPO po : valuePOS) {
      ReportValueGroupVO vo = new ReportValueGroupVO();
      vo.setAddDay(po.getAddDay());
      List<ReportValueVO> values = new ArrayList<>();
      Map<String, String[]> helper = po.help();
      String[] idArr = helper.get("id");
      String[] reportArr = helper.get("reportId");
      String[] airportArr = helper.get("airportId");
      String[] fieldIdArr = helper.get("fieldId");
      String[] valueArr = helper.get("value");
      String[] stateArr = helper.get("state");
      String[] modifyTimeArr = helper.get("modifyTime");
      String[] addTimeArr = helper.get("addTime");
      for (int i = 0; i < idArr.length; i++) {
        ReportValueVO valueVO = new ReportValueVO();
        valueVO.setId(Integer.valueOf(idArr[i]));
        valueVO.setReportId(Integer.valueOf(reportArr[i]));
        valueVO.setFieldId(Integer.valueOf(fieldIdArr[i]));
        valueVO.setAirportId(Integer.valueOf(airportArr[i]));
        FieldMinVO field = fieldMap.get(valueVO.getFieldId());
        switch (field.getType()) {
          case Integers:
            valueVO.setValue(Integer.valueOf(valueArr[i]));
            break;
          case Float:
            valueVO.setValue(Double.valueOf(valueArr[i]));
            break;
          case String:
          case Unknown:
            valueVO.setValue(valueArr[i]);
            break;
        }
        valueVO.setFieldName(field.getName());
        valueVO.setState(ValueStateEnum.valueOf(Short.valueOf(stateArr[i])));
        valueVO.setAddTime(DateUtils.formatDate(addTimeArr[i]));
        valueVO.setModifyTime(DateUtils.formatDate(modifyTimeArr[i]));
        values.add(valueVO);
      }
      vo.setValues(values);
      dataList.add(vo);
    }
    result.setDataList(dataList);
    return result;
  }

  public byte[] export2Excel(int[] reportId, int airportId, Date[] dateRange) throws ServiceActionException, IOException {
    if (dateRange == null || dateRange.length == 0) {
      throw new ServiceActionException(ActionCodeEnum.ExportExcelMustWithDate);
    }
    Map<Integer, FieldMinVO> fieldMap = new HashMap<>();
    fieldService.queryMinAll().forEach(fieldMinVO -> fieldMap.put(fieldMinVO.getId(), fieldMinVO));

    List<FieldWritePO> fieldWritePOS = reportValueMapper.queryForExcel(reportId, airportId, dateRange[0], dateRange[1]);
    Map<String, List<FieldWriteVO>> groupByAirport = new HashMap<>();
    fieldWritePOS.forEach(fieldWritePO -> {
      String name = fieldWritePO.getAirportName();
      if (groupByAirport.get(name) == null) {
        groupByAirport.put(name, new ArrayList<>());
      }
      FieldMinVO field = fieldMap.get(fieldWritePO.getFieldId());
      FieldWriteVO fieldWriteVO = new FieldWriteVO(fieldWritePO.getFieldId(), field.getName(), field.getType());
      switch (field.getType()) {
        case Integers:
          fieldWriteVO.setValue(Integer.valueOf(fieldWritePO.getValue()));
          break;
        case Float:
          fieldWriteVO.setValue(Double.valueOf(fieldWritePO.getValue()));
          break;
        case String:
        case Unknown:
          fieldWriteVO.setValue(fieldWritePO.getValue());
          break;
      }
      groupByAirport.get(name).add(fieldWriteVO);
    });
    // 分组之后 还有对 相同字段的值 进行累加
    Map<String, List<FieldWriteVO>> finalData = new HashMap<>();
    groupByAirport.forEach((s, fieldWriteVOS) -> {
      Map<Integer, FieldWriteVO> groupByField = new HashMap<>();
      fieldWriteVOS.forEach(fieldWriteVO -> {
        FieldWriteVO f = groupByField.get(fieldWriteVO.getFieldId());
        if (f != null) {
          Object newVal = null;
          switch (f.getType()) {
            case Integers:
              newVal = ((Integer) f.getValue()) + ((Integer) fieldWriteVO.getValue());
              break;
            case Float:
              newVal = ((Double) f.getValue()) + ((Double) fieldWriteVO.getValue());
              break;
            case String:
            case Unknown:
              newVal = f.getValue() + ((String) fieldWriteVO.getValue());
              break;
          }
          f.setValue(newVal);
        } else {
          groupByField.put(fieldWriteVO.getFieldId(), fieldWriteVO);
        }
      });
      finalData.put(s, new ArrayList<>(groupByField.values()));
    });
    return ExcelUtils.generateExcelFile("湖南空港实业股份有限公司", finalData, dateRange);
  }

}
