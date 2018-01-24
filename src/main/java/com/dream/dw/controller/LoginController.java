package com.dream.dw.controller;

import com.dream.dw.exception.ErrorCode;
import com.dream.dw.model.User;
import com.dream.dw.response.Responses;
import com.dream.dw.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Dream on 2018/1/13.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/login")
public class LoginController {

    @Autowired
    LoginService loginService;

    @RequestMapping(value = "loginByName", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity loginByName(@RequestBody User user) {
        if (user.getName() == null || user.getPassword() == null) {
            return Responses.error(ErrorCode.ErrorCode_0001);
        }
        Boolean result = loginService.loginByName(user);
        if (result) {
            return Responses.ok(true);
        } else {
            return Responses.error(ErrorCode.ErrorCode_0001);
        }
    }

    @RequestMapping(value = "loginByEmail")
    public ResponseEntity loginByEmail(@RequestBody User user) {
        if (user.getEmail() == null || user.getPassword() == null) {
            return Responses.error(ErrorCode.ErrorCode_0001);
        }
        Boolean result = loginService.loginByEmail(user);
        if (result) {
            return Responses.ok(true);
        } else {
            return Responses.error(ErrorCode.ErrorCode_0001);
        }
    }

    @RequestMapping(value = "register")
    public ResponseEntity registerUser(@RequestBody User user) {
        if (user.getPassword() == null) {
            return Responses.error(ErrorCode.ErrorCode_0003);
        }
        int result = loginService.registerUser(user);
        if (1 == result) {
            return Responses.ok(true);
        } else {
            return Responses.error(ErrorCode.ErrorCode_0003);
        }
    }

    @GetMapping(value = "active")
    public ResponseEntity active(@RequestParam String code) {
        Boolean result = loginService.activeUser(code);
        if (result) {
            return Responses.ok(true);
        } else {
            return Responses.error(ErrorCode.ErrorCode_0004);
        }
    }
}
