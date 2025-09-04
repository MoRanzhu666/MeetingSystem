package com.gp.mts.controller;

import com.gp.mts.bean.Appointment;
import com.gp.mts.service.AppointmentService;
import com.gp.mts.service.impl.AdminServiceImpl;
import com.gp.mts.service.impl.AppointmentServiceImpl;
import com.gp.mts.vo.AppointmentQueryVO;
import com.gp.mts.vo.AppointmentRegisterVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * 预约业务控制器（处理表单提交和数据渲染）
 */
@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentServiceImpl appointmentServiceImpl;

    /**
     * 处理预约登记表单提交
     */
    @PostMapping("/register")
    public String handleRegister(
            @Valid @RequestBody(required = false) AppointmentRegisterVO registerVO,
            BindingResult bindingResult,
            Model model) {

        System.out.println("register");
        // 表单验证失败，返回原页面并显示错误
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMsg", "表单填写有误，请检查后重试");
            return "register"; // 回到预约登记页
        }

        // 调用服务层处理业务
        Map<String, Object> result = appointmentServiceImpl.register(registerVO);

        // 处理业务结果
        if ((boolean) result.get("success")) {
            // 预约成功，携带结果跳转到成功页
            model.addAttribute("message", result.get("message"));
            model.addAttribute("appointmentId", result.get("appointmentId"));
            return "register-success"; // 预约成功页
        } else {
            // 预约失败，返回原页面显示错误
            model.addAttribute("errorMsg", result.get("message"));
            return "register";
        }
    }

    /**
     * 处理预约查询表单提交
     */
    @PostMapping("/query")
    public String handleQuery(
            @RequestBody(required = false) AppointmentQueryVO queryVO,
            Model model) {
        // 调用服务层查询
        Map<String, Object> result = appointmentServiceImpl.query(queryVO);
        // 绑定查询条件回显（方便用户修改）
        model.addAttribute("queryVO", queryVO);

        // 处理查询结果
        if ((boolean) result.get("success")) {
            model.addAttribute("appointments", result.get("data"));
            model.addAttribute("count", result.get("count"));
            System.out.println("result"+result);
        } else {
            model.addAttribute("errorMsg", result.get("message"));
        }

        return "query"; // 回到查询页展示结果
    }
}
