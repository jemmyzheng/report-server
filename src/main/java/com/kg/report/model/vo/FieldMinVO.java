package com.kg.report.model.vo;

import com.kg.report.model.enums.FieldTypeEnum;
import com.kg.report.model.po.FieldPO;
import com.kg.report.utils.HasTransConsumer;

import java.io.Serializable;

public class FieldMinVO implements Serializable, HasTransConsumer<FieldPO, FieldMinVO> {
  private int id;
  private String name;
  private FieldTypeEnum type;
  private boolean required;

  @Override
  public void transAction(FieldPO source, FieldMinVO self) {
    self.setType(FieldTypeEnum.valueOf(source.getType()));
    self.setRequired(source.getRequired() == 1);
  }

  @Override
  public void reTransAction(FieldMinVO self, FieldPO target) {

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
}
