package com.dream.dw.controller;

import com.dream.dw.model.User;
import com.dream.dw.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

    @RequestMapping(value = "loginbyname", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity loginByName(@RequestBody User user) {
        if (user.getName() == null || user.getPassword() == null) {
            return new ResponseEntity(0, HttpStatus.BAD_REQUEST);
        }
        Boolean result = loginService.loginByName(user);
        if (result) {
            return new ResponseEntity(user, HttpStatus.OK);
        } else {
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "loginbyemail")
    public ResponseEntity loginByEmail(@RequestBody User user) {
        if (user.getEmail() == null || user.getPassword() == null) {
            return new ResponseEntity(0, HttpStatus.BAD_REQUEST);
        }
        Boolean result = loginService.loginByEmail(user);
        if (result) {
            return new ResponseEntity(user, HttpStatus.OK);
        } else {
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "register")
    public ResponseEntity registerUser(@RequestBody User user) {
        if (user.getPassword() == null) {
            return new ResponseEntity(0, HttpStatus.BAD_REQUEST);
        }
        int result = loginService.registerUser(user);
        if (1 == result) {
            return new ResponseEntity(result, HttpStatus.OK);
        } else {
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "active")
    public ResponseEntity active(@RequestParam String code) {
        Boolean result = loginService.activeUser(code);
        if (result) {
            return new ResponseEntity(result, HttpStatus.OK);
        } else {
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }
    }

}
