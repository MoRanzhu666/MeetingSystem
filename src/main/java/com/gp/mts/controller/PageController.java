package com.gp.mts.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gp.mts.vo.AdminLoginVO;
import com.gp.mts.vo.AppointmentQueryVO;
import com.gp.mts.vo.AppointmentRegisterVO;

@Controller
public class PageController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
    // 必须添加：初始化表单绑定对象
    model.addAttribute("registerVO", new AppointmentRegisterVO());
    
    // 同时需要传递日期和时段数据（否则下拉框无选项）
    List<String> dates = new ArrayList<>();
    LocalDate today = LocalDate.now();
    for (int i = 1; i <= 7; i++) { // 未来7天的可选日期
        dates.add(today.plusDays(i).format(DateTimeFormatter.ISO_LOCAL_DATE));
    }
    List<String> times = Arrays.asList("09:30-10:00", "10:15-10:45", "14:00-14:30");
    
    model.addAttribute("dates", dates);
    model.addAttribute("times", times);

        return "register";
    }

    @GetMapping("/query")
    public String queryPage(Model model) {
        model.addAttribute("queryVO", new AppointmentQueryVO());
        return "query";
    }

}