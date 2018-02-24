package com.dream.dw.controller;

import com.dream.dw.response.Responses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class RootController {

    @GetMapping
    public ResponseEntity index() {
        return Responses.ok("Hello world!");
    }

}
