package com.dream.dw.controller;

import com.dream.dw.exception.ErrorCode;
import com.dream.dw.model.User;
import com.dream.dw.response.Responses;
import com.dream.dw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Dream on 2018/1/10.
 */
@CrossOrigin
@RestController
@RequestMapping( value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "getUserByUserId")
    public ResponseEntity getUserById(@RequestParam Long uid) {
        User user = userService.getUserById(uid);
        if (user != null) {
            return Responses.ok(user);
        } else {
            return Responses.error(ErrorCode.ErrorCode_0010);
        }
    }

    @GetMapping(value = "deleteUser")
    public ResponseEntity deleteUser(@RequestParam Long uid) {
        Boolean result = userService.deleteUser(uid);
        if (result) {
            return Responses.ok(true);
        } else {
            return Responses.error(ErrorCode.ErrorCode_0020);
        }
    }

    @GetMapping(value = "updatePwd")
    public ResponseEntity updatePwd(@RequestParam Long uid, @RequestParam String pwd) {
        Boolean result = userService.updatePwd(uid, pwd);
        if (result) {
            return Responses.ok(true);
        } else {
            return Responses.error(ErrorCode.ErrorCode_0030);
        }
    }

    @GetMapping(value = "updateName")
    public ResponseEntity updateName(@RequestParam Long uid, @RequestParam String name) {
        Boolean result = userService.updateName(uid, name);
        if (result) {
            return Responses.ok(true);
        } else {
            return Responses.error(ErrorCode.ErrorCode_0030);
        }
    }

    @GetMapping(value = "updateEmail")
    public ResponseEntity updateEmail(@RequestParam Long uid, @RequestParam String email) {
        Boolean result = userService.updateEmail(uid, email);
        if (result) {
            return Responses.ok(true);
        } else {
            return Responses.error(ErrorCode.ErrorCode_0030);
        }
    }


}