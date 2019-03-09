package com.kg.report;

import com.kg.report.model.enums.RoleEnum;
import com.kg.report.model.enums.UserStateEnum;
import com.kg.report.model.vo.DeptVO;
import com.kg.report.model.vo.UserVO;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component("currentUser")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CurrentUser {
  protected int userId;
  protected String name;
  protected String phone;
  protected int deptId;
  protected RoleEnum role;
  protected String deptName;
  protected UserStateEnum state;

  public void setUserDetails(UserVO userVO, DeptVO deptVO) {
    this.userId = userVO.getUserId();
    this.name = userVO.getName();
    this.phone = userVO.getPhone();
    this.deptId = userVO.getDeptId();
    this.role = userVO.getRole();
    this.deptName = deptVO.getName();
    this.state = userVO.getState();
  }

  public void clear() {
    this.name = null;
    this.userId = 0;
    this.phone = null;
    this.state = UserStateEnum.Unknown;
    this.deptName = null;
    this.deptId = 0;
    this.role = RoleEnum.Unknown;
  }

  public int getUserId() {
    return userId;
  }

  public String getName() {
    return name;
  }

  public String getPhone() {
    return phone;
  }

  public int getDeptId() {
    return deptId;
  }

  public String getDeptName() {
    return deptName;
  }

  public UserStateEnum getState() {
    return state;
  }

  public RoleEnum getRole() {
    return role;
  }
}
