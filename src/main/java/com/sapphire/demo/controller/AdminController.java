package com.sapphire.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    // size = 一页显示7个问题
    public String hello() {
        return "admin";
    }
}
