package com.kg.report.model.vo;

import com.kg.report.model.enums.RoleEnum;
import com.kg.report.model.enums.UserStateEnum;
import com.kg.report.model.po.UserPO;
import com.kg.report.utils.HasTransConsumer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.io.Serializable;

public class UserCreatorVO implements Serializable, HasTransConsumer<UserPO, UserCreatorVO> {

  private static final long serialVersionUID = 8241726512600049665L;
  @Positive
  private int deptId; // 部门ID

  @NotBlank
  private String name; // 名称

  @NotBlank
  private String phone; // 手机

  @NotBlank
  private String password; // 登录密码

  private UserStateEnum state = UserStateEnum.Valid;

  private RoleEnum role = RoleEnum.Normal;

  @Override
  public void transAction(UserPO source, UserCreatorVO self) {
  }

  @Override
  public void reTransAction(UserCreatorVO self, UserPO target) {
    target.setState(self.getState().getValue());
    target.setRole(self.getRole().getValue());
  }

  public int getDeptId() {
    return deptId;
  }

  public void setDeptId(int deptId) {
    this.deptId = deptId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public UserStateEnum getState() {
    return state;
  }

  public RoleEnum getRole() {
    return role;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
