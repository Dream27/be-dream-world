package com.dream.dw.service;

import com.dream.dw.model.User;

import java.util.List;

/**
 * Created by Dream on 2017/12/29.
 */
public interface UserService {
    List<User> getAllUsers();

    User getUserById(Long userId);

    boolean deleteUser(Long userId);

    boolean updatePwd(Long userId, String newPwd);
    boolean updateName(Long userId, String newName);
    boolean updateEmail(Long userId, String newEmail);
}
