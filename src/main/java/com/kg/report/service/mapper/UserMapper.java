package com.kg.report.service.mapper;

import com.kg.report.model.po.UserPO;

import java.util.List;

public interface UserMapper {
  UserPO getUser(String phone);

  UserPO getUserById(int userId);

  String userExist(String phone);

  int editUser(int userId, String name, String phone, int deptId, String password);

  int updateUserState(int userId, short state);

  int addUser(UserPO po);

  List<UserPO> queryAll();

}
