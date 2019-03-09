package com.kg.report.model.vo;

import com.kg.report.model.enums.FieldTypeEnum;
import com.kg.report.model.po.FieldPO;
import com.kg.report.utils.HasTransConsumer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class FieldCreatorVO implements Serializable, HasTransConsumer<FieldPO, FieldCreatorVO> {
  private static final long serialVersionUID = 1732576124028462442L;
  @NotBlank
  private String name;
  @NotNull
  private FieldTypeEnum type;
  private boolean required = true;

  @Override
  public void transAction(FieldPO source, FieldCreatorVO self) {
  }

  @Override
  public void reTransAction(FieldCreatorVO self, FieldPO target) {
    target.setRequired(self.isRequired() ? (short) 1 : (short) 0);
    target.setType(self.getType().getValue());
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
