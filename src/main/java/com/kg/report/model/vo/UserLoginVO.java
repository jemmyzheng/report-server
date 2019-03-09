package com.kg.report.model.vo;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class UserLoginVO implements Serializable {
  private static final long serialVersionUID = 3327227453895377870L;
  @NotBlank
  private String phone;
  @NotBlank
  private String password;

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
