package com.dream.dw.service;

import com.dream.dw.model.User;

import java.util.List;

/**
 * Created by Dream on 2017/12/29.
 */
public interface LoginService {

    boolean loginByName(User user);

    boolean loginByEmail(User user);

    int registerUser(User user);

    boolean sendActiveEmail(User user);

    boolean activeUser(String code);
}
