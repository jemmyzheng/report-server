package com.kg.report.model.vo;

import com.kg.report.model.enums.FieldTypeEnum;
import com.kg.report.model.po.FieldPO;
import com.kg.report.utils.HasTransConsumer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

public class FieldEditorVO implements Serializable, HasTransConsumer<FieldPO, FieldEditorVO> {
  private static final long serialVersionUID = -4371343637829053576L;
  @Positive
  private int id;
  @NotBlank
  private String name;
  @NotNull
  private FieldTypeEnum type;
  private boolean required = true;

  @Override
  public void transAction(FieldPO source, FieldEditorVO self) {
  }

  @Override
  public void reTransAction(FieldEditorVO self, FieldPO target) {
    target.setRequired(self.isRequired() ? (short) 1 : (short) 0);
    target.setType(self.getType().getValue());
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
