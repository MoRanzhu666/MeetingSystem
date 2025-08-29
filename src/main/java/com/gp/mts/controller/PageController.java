package com.gp.mts.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// PageController.java - 删除管理员相关路由
@Controller
public class PageController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/query")
    public String queryPage() {
        return "query";
    }

    // 删除 admin 相关的路由
}