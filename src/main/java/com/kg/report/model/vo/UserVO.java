package com.kg.report.model.vo;

import com.kg.report.model.enums.RoleEnum;
import com.kg.report.model.enums.UserStateEnum;
import com.kg.report.model.po.UserPO;
import com.kg.report.utils.HasTransConsumer;

import java.io.Serializable;
import java.util.Date;

public class UserVO implements Serializable, HasTransConsumer<UserPO, UserVO> {
  private static final long serialVersionUID = -175539245610479028L;
  private int userId; // ID

  private int deptId; // 部门ID

  private String name; // 名称

  private String phone; // 手机

  private UserStateEnum state;

  private RoleEnum role;

  private Date addTime;

  @Override
  public void transAction(UserPO source, UserVO self) {
    self.setState(UserStateEnum.valueOf(source.getState()));
    self.setRole(RoleEnum.valueOf(source.getRole()));
  }

  @Override
  public void reTransAction(UserVO self, UserPO target) {

  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
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

  public void setState(UserStateEnum state) {
    this.state = state;
  }

  public RoleEnum getRole() {
    return role;
  }

  public void setRole(RoleEnum role) {
    this.role = role;
  }

  public Date getAddTime() {
    return addTime;
  }

  public void setAddTime(Date addTime) {
    this.addTime = addTime;
  }
}
