package com.dream.dw.controller;

import com.dream.dw.exception.BaseException;
import com.dream.dw.model.User;
import com.dream.dw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Dream on 2018/1/10.
 */
@CrossOrigin
@RestController
@RequestMapping( value = "/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(value = "alluser")
    public ResponseEntity getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity(users, HttpStatus.OK);
    }

    @GetMapping(value = "queryuser")
    public ResponseEntity getUserById(@RequestParam Long uid) {
        User user = userService.getUserById(uid);
        if (user != null) {
            return new ResponseEntity(user, HttpStatus.OK);
        }

        BaseException exception = new BaseException();
        exception.setCode(1001);
        exception.setMessage("user not found by id!");
        return new ResponseEntity(exception, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "delete")
    public ResponseEntity deleteUser(@RequestParam Long uid) {
        Boolean result = userService.deleteUser(uid);
        if (result) {
            return new ResponseEntity(result, HttpStatus.OK);
        } else {
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "updatepwd")
    public ResponseEntity updatePwd(@RequestParam Long uid, @RequestParam String pwd) {
        Boolean result = userService.updatePwd(uid, pwd);
        if (result) {
            return new ResponseEntity(result, HttpStatus.OK);
        } else {
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "updatename")
    public ResponseEntity updateName(@RequestParam Long uid, @RequestParam String name) {
        Boolean result = userService.updateName(uid, name);
        if (result) {
            return new ResponseEntity(result, HttpStatus.OK);
        } else {
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "updateemail")
    public ResponseEntity updateEmail(@RequestParam Long uid, @RequestParam String email) {
        Boolean result = userService.updateEmail(uid, email);
        if (result) {
            return new ResponseEntity(result, HttpStatus.OK);
        } else {
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }
    }


}