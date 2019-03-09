package com.kg.report.service;

import com.kg.report.model.enums.ActionCodeEnum;
import com.kg.report.model.po.FieldPO;
import com.kg.report.model.po.ReportFieldListPO;
import com.kg.report.model.po.ReportFieldPO;
import com.kg.report.model.po.ReportPO;
import com.kg.report.model.vo.*;
import com.kg.report.service.mapper.FieldMapper;
import com.kg.report.service.mapper.ReportFieldMapper;
import com.kg.report.service.mapper.ReportMapper;
import com.kg.report.service.mapper.ReportValueMapper;
import com.kg.report.utils.Converter;
import com.kg.report.utils.exception.ServiceActionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {
  protected Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  ReportMapper reportMapper;

  @Autowired
  ReportFieldMapper reportFieldMapper;

  @Autowired
  ReportValueMapper reportValueMapper;

  @Autowired
  FieldMapper fieldMapper;

  @Resource(name = "transactionManager")
  private PlatformTransactionManager platformTransactionManager;

  public int add(ReportCreatorVO creatorVO, int userId) throws ServiceActionException {
    ReportPO po = Converter.copyProperties(creatorVO, ReportPO.class, "type", "fields");
    po.setModifyUserId(userId);
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
    TransactionStatus status = platformTransactionManager.getTransaction(def);
    try {
      int insertReport = reportMapper.insert(po);
      if (insertReport > 0) {
        List<ReportFieldPO> fieldPOS = new ArrayList<>();
        creatorVO.getFields().forEach(fieldId -> fieldPOS.add(new ReportFieldPO(po.getId(), fieldId)));
        if (reportFieldMapper.batchAddReportField(fieldPOS) > 0) {
          platformTransactionManager.commit(status);
          return po.getId();
        }
        platformTransactionManager.rollback(status);
        throw new ServiceActionException(ActionCodeEnum.Failed);
      }
      platformTransactionManager.rollback(status);
      throw new ServiceActionException(ActionCodeEnum.Failed);
    } catch (Exception ex) {
      logger.error("添加报表出错", ex);
      platformTransactionManager.rollback(status);
      throw new ServiceActionException(ActionCodeEnum.Failed);
    }
  }

  public void delete(int reportId) throws ServiceActionException {
    ReportPO po = reportMapper.selectById(reportId);
    if (po == null) throw new ServiceActionException(ActionCodeEnum.ReportNotExist);

    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
    TransactionStatus status = platformTransactionManager.getTransaction(def);
    try {
      int reportDel = reportMapper.deleteById(reportId);
      if (reportDel <= 0) {
        platformTransactionManager.rollback(status);
        throw new ServiceActionException(ActionCodeEnum.Failed);
      }
      int reportFieldDel = reportFieldMapper.deleteByReportId(reportId);
      if (reportFieldDel <= 0) {
        platformTransactionManager.rollback(status);
        throw new ServiceActionException(ActionCodeEnum.Failed);
      }
      int reportValueDel = reportValueMapper.deleteByReportId(reportId);
      if (reportValueDel < 0) {
        platformTransactionManager.rollback(status);
        throw new ServiceActionException(ActionCodeEnum.Failed);
      }
      platformTransactionManager.commit(status);
    } catch (ServiceActionException ex) {
      throw ex;
    } catch (Exception ex) {
      logger.error("删除报表出错", ex);
      platformTransactionManager.rollback(status);
      throw new ServiceActionException(ActionCodeEnum.Failed);
    }
  }

  public ReportVO getById(int reportId) throws ServiceActionException {
    ReportPO po = reportMapper.selectById(reportId);
    if (po == null) throw new ServiceActionException(ActionCodeEnum.ReportNotExist);
    return Converter.copyProperties(po, ReportVO.class, "type");
  }

  public ReportDetailVO getDetailById(int reportId) throws ServiceActionException {
    ReportPO po = reportMapper.selectById(reportId);
    if (po == null) throw new ServiceActionException(ActionCodeEnum.ReportNotExist);
    ReportDetailVO detailVO = Converter.copyProperties(po, ReportDetailVO.class, "type");
    detailVO.setFields(getFields(reportId));
    return detailVO;
  }

  public List<ReportVO> getByDept(int deptId) {
    List<ReportPO> pos = reportMapper.selectByDeptId(deptId);
    return Converter.copyList(pos, ReportVO.class, "type");
  }

  public List<ReportVO> getAll() {
    List<ReportPO> pos = reportMapper.selectAll();
    return Converter.copyList(pos, ReportVO.class, "type");
  }

  public List<ReportListVO> getReportWithField(int deptId) {
    List<ReportFieldListPO> pos = reportMapper.selectReportWithFieldId(deptId);
    return Converter.copyList(pos, ReportListVO.class, "type");
  }

  public void update(ReportEditorVO editorVO, int userId) throws ServiceActionException {
    ReportPO origin = reportMapper.selectById(editorVO.getId());
    if (origin == null) throw new ServiceActionException(ActionCodeEnum.ReportNotExist);
    ReportPO target = Converter.copyProperties(editorVO, ReportPO.class, "type");
    target.setModifyUserId(userId);
    int res = reportMapper.updateBy(target);
    if (res <= 0) throw new ServiceActionException(ActionCodeEnum.Failed);
  }

  public void setFields(ReportFieldVO reportFieldVO) throws ServiceActionException {
    List<ReportFieldPO> pos = new ArrayList<>();
    reportFieldVO.getFieldIds().forEach(fieldId -> {
      ReportFieldPO po = new ReportFieldPO();
      po.setFieldId(fieldId);
      po.setReportId(reportFieldVO.getReportId());
      pos.add(po);
    });
    int res = reportFieldMapper.batchAddReportField(pos);
    if (res <= 0) throw new ServiceActionException(ActionCodeEnum.Failed);
  }

  public void removeFieldFrom(ReportRemoveFieldVO removeFieldVO) throws ServiceActionException {
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
    TransactionStatus status = platformTransactionManager.getTransaction(def);
    try {
      int res = reportFieldMapper.deleteByPrimaryKey(removeFieldVO.getReportId(), removeFieldVO.getFieldId());
      // todo 暂时不作联动删除 防止数据丢失 int valueDelete = reportValueMapper.deleteByReportIdAndFieldId(removeFieldVO.getReportId(), removeFieldVO.getFieldId());
      if (res <=0) {
        platformTransactionManager.rollback(status);
        throw new ServiceActionException(ActionCodeEnum.Failed);
      }
      platformTransactionManager.commit(status);
    } catch (ServiceActionException ex) {
      throw ex;
    } catch (Exception ex) {
      logger.error("删除报表字段出错", ex);
      platformTransactionManager.rollback(status);
      throw new ServiceActionException(ActionCodeEnum.Failed);
    }
  }

  public List<FieldMinVO> getFields(int reportId) throws ServiceActionException {
    ReportPO origin = reportMapper.selectById(reportId);
    if (origin == null) throw new ServiceActionException(ActionCodeEnum.ReportNotExist);
    List<FieldPO> fields = reportFieldMapper.selectFieldBy(reportId);
    return Converter.copyList(fields, FieldMinVO.class, "type", "required");
  }

}
