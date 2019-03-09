package com.kg.report.model.vo;

import com.kg.report.model.enums.FieldTypeEnum;
import com.kg.report.model.po.FieldPO;
import com.kg.report.utils.HasTransConsumer;

import java.io.Serializable;
import java.util.Date;

public class FieldVO implements Serializable, HasTransConsumer<FieldPO, FieldVO> {
  private static final long serialVersionUID = 1732576124028462442L;
  private int id;
  private String name;
  private FieldTypeEnum type;
  private boolean required;
  private Date addTime;

  @Override
  public void transAction(FieldPO source, FieldVO self) {
    self.setType(FieldTypeEnum.valueOf(source.getType()));
    self.setRequired(source.getRequired() == 1);
  }

  @Override
  public void reTransAction(FieldVO self, FieldPO target) {

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

  public FieldTypeEnum getType() {
    return type;
  }

  public void setType(FieldTypeEnum type) {
    this.type = type;
  }

  public boolean isRequired() {
    return required;
  }

  public void setRequired(boolean required) {
    this.required = required;
  }

  public Date getAddTime() {
    return addTime;
  }

  public void setAddTime(Date addTime) {
    this.addTime = addTime;
  }
}
