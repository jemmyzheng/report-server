package com.kg.report.model.vo;

import com.kg.report.model.po.DeptPO;
import com.kg.report.utils.HasTransConsumer;

import java.io.Serializable;
import java.util.Date;

public class DeptVO implements Serializable, HasTransConsumer<DeptPO, DeptVO> {
  private static final long serialVersionUID = -2577632188414963517L;
  private int id;
  private String name;
  private Date addTime;

  @Override
  public void transAction(DeptPO source, DeptVO self) {

  }

  @Override
  public void reTransAction(DeptVO self, DeptPO target) {

  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getAddTime() {
    return addTime;
  }

  public void setAddTime(Date addTime) {
    this.addTime = addTime;
  }
}
