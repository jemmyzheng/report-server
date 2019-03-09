package com.kg.report.service;

import com.kg.report.model.enums.ActionCodeEnum;
import com.kg.report.model.enums.UserStateEnum;
import com.kg.report.model.po.UserPO;
import com.kg.report.model.vo.UserCreatorVO;
import com.kg.report.model.vo.UserEditorVO;
import com.kg.report.model.vo.UserVO;
import com.kg.report.service.mapper.UserMapper;
import com.kg.report.utils.Converter;
import com.kg.report.utils.PasswordUtil;
import com.kg.report.utils.exception.ServiceActionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

  @Autowired
  private UserMapper userMapper;

  public UserVO login(String phone, String password) throws ServiceActionException {
    UserPO po= userMapper.getUser(phone);
    if(po == null) {
      throw new ServiceActionException(ActionCodeEnum.UserNotExist);
    }
    if (po.getState() == UserStateEnum.Locked.getValue()) {
      throw new ServiceActionException(ActionCodeEnum.UserLocked);
    }
    if(!PasswordUtil.verifyPassword(password, po.getPassword())) {
      throw new ServiceActionException(ActionCodeEnum.PasswordError);
    }
    return Converter.copyProperties(po, UserVO.class, "state", "role");
  }

  public int register(UserCreatorVO creatorVO) throws ServiceActionException {
    boolean isPhoneExist = userMapper.userExist(creatorVO.getPhone()) != null;
    if (isPhoneExist) throw new ServiceActionException(ActionCodeEnum.UserIsExist);
    UserPO po = Converter.copyProperties(creatorVO, UserPO.class, "state", "role");
    po.setPassword(PasswordUtil.generateDBPassword(creatorVO.getPassword()));
    int res = userMapper.addUser(po);
    if (res > 0) return po.getUserId();
    throw new ServiceActionException(ActionCodeEnum.Failed);
  }

  public List<UserVO> getAllUser() {
    List<UserPO> pos = userMapper.queryAll();
    return Converter.copyList(pos, UserVO.class, "state", "role");
  }

  public UserVO getUserDetail(int userId) throws ServiceActionException {
    UserPO po = userMapper.getUserById(userId);
    if (po == null) throw new ServiceActionException(ActionCodeEnum.UserNotExist);
    return Converter.copyProperties(po, UserVO.class, "state", "role", "password");
  }

  public void editUser(UserEditorVO editorVO) throws ServiceActionException {
    UserPO target = userMapper.getUserById(editorVO.getUserId());
    if (target == null) throw new ServiceActionException(ActionCodeEnum.UserNotExist);
    if (!target.getPhone().equals(editorVO.getPhone())) {
      boolean isPhoneExist = userMapper.userExist(editorVO.getPhone()) != null;
      if (isPhoneExist) throw new ServiceActionException(ActionCodeEnum.PhoneIsExist);
    }
    String password = editorVO.getPassword() != null ? PasswordUtil.generateDBPassword(editorVO.getPassword()) : null;
    int res = userMapper.editUser(editorVO.getUserId(), editorVO.getName(), editorVO.getPhone(), editorVO.getDeptId(), password);
    if (res <= 0) throw new ServiceActionException(ActionCodeEnum.Failed);
  }

  public void editUserState(int userId, UserStateEnum state) throws ServiceActionException {
    UserPO target = userMapper.getUserById(userId);
    if (target == null) throw new ServiceActionException(ActionCodeEnum.UserNotExist);
    if ( userMapper.updateUserState(userId, state.getValue()) <= 0 ) {
      throw new ServiceActionException(ActionCodeEnum.Failed);
    }
  }

}
