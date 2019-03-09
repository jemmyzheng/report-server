package com.kg.report.model.po;

import java.util.Date;

public class UserPO {

  private int userId; // ID

  private int deptId; // 部门ID

  private String name; // 名称

  private String phone; // 手机

  private String password; // 密码

  private short state; // 状态

  private short role; // 角色

  private Date addTime; // 创建时间

  public UserPO() {
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public short getState() {
    return state;
  }

  public void setState(short state) {
    this.state = state;
  }

  public short getRole() {
    return role;
  }

  public void setRole(short role) {
    this.role = role;
  }

  public Date getAddTime() {
    return addTime;
  }

  public void setAddTime(Date addTime) {
    this.addTime = addTime;
  }
}
