package com.dream.dw.service.impl;

import com.dream.dw.dao.UserExample;
import com.dream.dw.dao.UserMapper;
import com.dream.dw.model.User;
import com.dream.dw.service.LoginService;
import com.dream.dw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Dream on 2018/1/10.
 */
@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LoginService loginService;

    @Override
    public List<User> getAllUsers() {
        UserExample userExample = new UserExample();
        userExample.createCriteria();
        return userMapper.selectByExample(userExample);
    }

    @Override
    public User getUserById(Long userId) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserIdEqualTo(userId);
        List<User> users = userMapper.selectByExample(userExample);
        if(users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    @Override
    public boolean deleteUser(Long userId) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserIdEqualTo(userId);
        int result = userMapper.deleteByExample(userExample);
        return result == 0 ? false:true;
    }

    @Override
    public boolean updatePwd(Long userId, String newPwd) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserIdEqualTo(userId);
        User user = new User();
        user.setPassword(newPwd);
        int result = userMapper.updateByExampleSelective(user, userExample);
        return result == 0 ? false:true;
    }

    @Override
    public boolean updateName(Long userId, String newName) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserIdEqualTo(userId);
        User user = new User();
        user.setName(newName);
        int result = userMapper.updateByExampleSelective(user, userExample);
        return result == 0 ? false:true;
    }

    @Override
    public boolean updateEmail(Long userId, String newEmail) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserIdEqualTo(userId);
        User user = new User();
        user.setEmail(newEmail);
        user.setStatus(1);
        int result = userMapper.updateByExampleSelective(user, userExample);

        //发送激活邮件
        loginService.sendActiveEmail(user);
        return result == 0 ? false:true;
    }
}
