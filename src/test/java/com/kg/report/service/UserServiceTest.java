package com.kg.report.service;

import com.kg.report.model.enums.ActionCodeEnum;
import com.kg.report.model.enums.UserStateEnum;
import com.kg.report.model.vo.UserCreatorVO;
import com.kg.report.model.vo.UserEditorVO;
import com.kg.report.model.vo.UserVO;
import com.kg.report.utils.exception.ServiceActionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {

  @Autowired
  UserService userService;

  @Test
  public void login() throws ServiceActionException {
    try {
      userService.login("18888888888", "123456");
    } catch (ServiceActionException ex) {
      assertEquals(ActionCodeEnum.UserNotExist, ex.getActionCode());
    }
    UserCreatorVO creatorVO = new UserCreatorVO();
    creatorVO.setPhone("18888888888");
    creatorVO.setDeptId(1);
    creatorVO.setName("test");
    creatorVO.setPassword("123456");
    int userId = userService.register(creatorVO);
    assertTrue(userId > 0);
    UserVO userVO = userService.login(creatorVO.getPhone(), creatorVO.getPassword());
    assertEquals(userVO.getUserId(), userId);
  }

  @Test
  public void getAllUser() throws ServiceActionException {
    UserCreatorVO creatorVO = new UserCreatorVO();
    creatorVO.setPhone("18888888888");
    creatorVO.setDeptId(1);
    creatorVO.setName("test");
    creatorVO.setPassword("123456");
    int userId = userService.register(creatorVO);
    assertTrue(userId > 0);
    List<UserVO> users = userService.getAllUser();
    assertTrue(users.size() > 0);
  }

  @Test
  public void editUser() throws ServiceActionException {
    UserCreatorVO creatorVO = new UserCreatorVO();
    creatorVO.setPhone("18888888888");
    creatorVO.setDeptId(1);
    creatorVO.setName("test");
    creatorVO.setPassword("123456");
    int userId = userService.register(creatorVO);
    assertTrue(userId > 0);
    UserEditorVO editorVO = new UserEditorVO();
    editorVO.setDeptId(2);
    editorVO.setName("edit");
    editorVO.setPhone("17788888888");
    editorVO.setUserId(userId);
    userService.editUser(editorVO);
    UserVO user = userService.getUserDetail(userId);
    assertEquals(editorVO.getPhone(), user.getPhone());
    assertEquals(editorVO.getName(), user.getName());
    editorVO.setPassword("123123");
    userService.editUser(editorVO);
    UserVO userVO = userService.login(editorVO.getPhone(), editorVO.getPassword());
    assertEquals(userVO.getUserId(), userId);
  }

  @Test
  public void editUserState() throws ServiceActionException {
    UserCreatorVO creatorVO = new UserCreatorVO();
    creatorVO.setPhone("18888888888");
    creatorVO.setDeptId(1);
    creatorVO.setName("test");
    creatorVO.setPassword("123456");
    int userId = userService.register(creatorVO);
    UserVO user = userService.getUserDetail(userId);
    assertEquals(UserStateEnum.Valid, user.getState());
    userService.editUserState(userId, UserStateEnum.Deleted);
    try {
      userService.login(creatorVO.getPhone(), creatorVO.getPassword());
    } catch (ServiceActionException ex) {
      assertEquals(ActionCodeEnum.UserNotExist, ex.getActionCode());
    }
    user = userService.getUserDetail(userId);
    assertEquals(user.getUserId(), userId);
    int userNewId = userService.register(creatorVO);
    assertEquals(1, userNewId - userId);
  }
}