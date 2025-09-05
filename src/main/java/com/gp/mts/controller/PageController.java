package com.gp.mts.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.gp.mts.service.impl.PageService;
import com.gp.mts.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gp.mts.vo.AdminLoginVO;
import com.gp.mts.vo.AppointmentQueryVO;
import com.gp.mts.vo.AppointmentRegisterVO;

@Controller
public class PageController {

    @Autowired
    private PageService pageService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        pageService.initRegisterPageModel(model);
        return PageUtils.toRegisterPage(model);
    }

    @GetMapping("/query")
    public String queryPage(Model model) {
        pageService.initQueryPageModel(model);
        return PageUtils.toQueryPage(model);
    }

}