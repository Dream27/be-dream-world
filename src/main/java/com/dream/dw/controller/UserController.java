package com.dream.dw.controller;

import com.dream.dw.exception.ErrorCode;
import com.dream.dw.model.User;
import com.dream.dw.response.Responses;
import com.dream.dw.service.LoginService;
import com.dream.dw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Dream on 2018/1/10.
 */
@RestController
@RequestMapping( value = "/user")
public class UserController {

    @Autowired
    LoginService loginService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "loginByName")
    public ResponseEntity loginByName(@RequestBody User user) {
        if (user.getName() == null || user.getPassword() == null) {
            return Responses.error(ErrorCode.ErrorCode_1000);
        }
        Boolean result = loginService.loginByName(user);
        if (result) {
            return Responses.ok(true);
        } else {
            return Responses.error(ErrorCode.ErrorCode_1000);
        }
    }

    @PostMapping(value = "loginByEmail")
    public ResponseEntity loginByEmail(@RequestBody User user) {
        if (user.getEmail() == null || user.getPassword() == null) {
            return Responses.error(ErrorCode.ErrorCode_1000);
        }
        Boolean result = loginService.loginByEmail(user);
        if (result) {
            return Responses.ok(true);
        } else {
            return Responses.error(ErrorCode.ErrorCode_1000);
        }
    }

    @PostMapping(value = "register")
    public ResponseEntity registerUser(@RequestBody User user) {
        if (user.getPassword() == null) {
            return Responses.error(ErrorCode.ErrorCode_1002);
        }
        int result = loginService.registerUser(user);
        if (1 == result) {
            return Responses.ok(true);
        } else {
            return Responses.error(ErrorCode.ErrorCode_1002);
        }
    }

    @PutMapping(value = "active")
    public ResponseEntity active(@RequestParam String code) {
        Boolean result = loginService.activeUser(code);
        if (result) {
            return Responses.ok(true);
        } else {
            return Responses.error(ErrorCode.ErrorCode_1003);
        }
    }

    @GetMapping(value = "getUserByUserId")
    public ResponseEntity getUserById(@RequestParam Long uid) {
        User user = userService.getUserById(uid);
        if (user != null) {
            return Responses.ok(user);
        } else {
            return Responses.error(ErrorCode.ErrorCode_1004);
        }
    }

    @GetMapping(value = "deleteUser")
    public ResponseEntity deleteUser(@RequestParam Long uid) {
        Boolean result = userService.deleteUser(uid);
        if (result) {
            return Responses.ok(true);
        } else {
            return Responses.error(ErrorCode.ErrorCode_1005);
        }
    }

    @GetMapping(value = "updatePwd")
    public ResponseEntity updatePwd(@RequestParam Long uid, @RequestParam String pwd) {
        Boolean result = userService.updatePwd(uid, pwd);
        if (result) {
            return Responses.ok(true);
        } else {
            return Responses.error(ErrorCode.ErrorCode_1006);
        }
    }

    @GetMapping(value = "updateName")
    public ResponseEntity updateName(@RequestParam Long uid, @RequestParam String name) {
        Boolean result = userService.updateName(uid, name);
        if (result) {
            return Responses.ok(true);
        } else {
            return Responses.error(ErrorCode.ErrorCode_1006);
        }
    }

    @GetMapping(value = "updateEmail")
    public ResponseEntity updateEmail(@RequestParam Long uid, @RequestParam String email) {
        Boolean result = userService.updateEmail(uid, email);
        if (result) {
            return Responses.ok(true);
        } else {
            return Responses.error(ErrorCode.ErrorCode_1006);
        }
    }


}