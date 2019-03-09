package com.kg.report.controller;

import com.kg.report.CurrentUser;
import com.kg.report.model.enums.UserStateEnum;
import com.kg.report.model.vo.*;
import com.kg.report.service.DeptService;
import com.kg.report.service.UserService;
import com.kg.report.utils.ApiResult;
import com.kg.report.utils.DoAuth;
import com.kg.report.utils.exception.ServiceActionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
  @Autowired
  UserService userService;

  @Autowired
  DeptService deptService;

  @Autowired
  CurrentUser currentUser;

  @PostMapping("/api/login")
  public ApiResult<UserVO> login(@RequestBody @Valid UserLoginVO loginVO) throws ServiceActionException {
    UserVO userVO = userService.login(loginVO.getPhone(), loginVO.getPassword());
    DeptVO deptVO = deptService.getById(userVO.getDeptId());
    currentUser.setUserDetails(userVO, deptVO);
    return ApiResult.hasDone(userVO);
  }

  @PostMapping("/api/users")
  @DoAuth
  public ApiResult<Integer> register(@RequestBody @Valid UserCreatorVO creatorVO) throws ServiceActionException {
    int result = userService.register(creatorVO);
    return ApiResult.hasDone(result);
  }

  @GetMapping("/api/users")
  @DoAuth
  public ApiResult<List<UserVO>> getAllUsers() {
    List<UserVO> result = userService.getAllUser();
    return ApiResult.hasDone(result);
  }

  @GetMapping("/api/currentUser")
  public ApiResult getCurrentUser() {
    Map<String, Object> result = new HashMap<>();
    result.put("userId", currentUser.getUserId());
    result.put("name", currentUser.getName());
    result.put("phone", currentUser.getPhone());
    result.put("deptId", currentUser.getDeptId());
    result.put("role", currentUser.getRole().name());
    result.put("deptName", currentUser.getDeptName());
    return ApiResult.hasDone(result);
  }

  @GetMapping("/api/users/{userId}")
  @DoAuth
  public ApiResult<UserVO> getUserById(@PathVariable("userId") int userId) throws ServiceActionException {
    UserVO userVO = userService.getUserDetail(userId);
    return ApiResult.hasDone(userVO);
  }

  @PutMapping("/api/users/{userId}")
  @DoAuth
  public ApiResult editUser(@PathVariable("userId") int userId, @RequestBody @Valid UserEditorVO editorVO) throws ServiceActionException {
    editorVO.setUserId(userId);
    userService.editUser(editorVO);
    return ApiResult.hasDone();
  }

  @PutMapping("/api/users/{userId}/lock")
  @DoAuth
  public ApiResult lockUser(@PathVariable("userId") int userId) throws ServiceActionException {
    userService.editUserState(userId, UserStateEnum.Locked);
    return ApiResult.hasDone();
  }

  @PutMapping("/api/users/{userId}/enable")
  @DoAuth
  public ApiResult enableUser(@PathVariable("userId") int userId) throws ServiceActionException {
    userService.editUserState(userId, UserStateEnum.Valid);
    return ApiResult.hasDone();
  }

  @DeleteMapping("/api/users/{userId}")
  @DoAuth
  public ApiResult deleteUser(@PathVariable("userId") int userId) throws ServiceActionException {
    userService.editUserState(userId, UserStateEnum.Deleted);
    return ApiResult.hasDone();
  }

}
