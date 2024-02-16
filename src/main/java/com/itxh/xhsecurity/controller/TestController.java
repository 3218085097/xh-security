package com.itxh.xhsecurity.controller;

import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class TestController {

    //匿名认证
    @GetMapping("/")
    public String method(@CurrentSecurityContext SecurityContext context) {
        return context.getAuthentication().getName();
    }
}
