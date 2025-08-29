package com.gp.mts.controller;

import com.gp.mts.bean.Appointment;
import com.gp.mts.service.AdminService;
import com.gp.mts.service.AppointmentService;
import com.gp.mts.vo.AdminLoginVO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理员控制器（处理后台管理功能）
 */
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final AppointmentService appointmentService;

    /**
     * 处理管理员登录
     */
    @PostMapping("/login")
    public String handleLogin(
            AdminLoginVO loginVO,
            Model model,
            HttpSession session) {

        // 验证登录
        boolean loginSuccess = adminService.login(loginVO.getUsername(), loginVO.getPassword());

        if (loginSuccess) {
            // 登录成功，保存会话并跳转到后台首页
            session.setAttribute("admin", loginVO.getUsername());
            return "redirect:/admin/dashboard";
        } else {
            // 登录失败，返回登录页
            model.addAttribute("errorMsg", "用户名或密码错误");
            return "admin/login";
        }
    }

    @GetMapping("/login")
    public String adminLoginPage() {
        return "admin/login";
    }

    /**
     * 管理员后台首页
     */
    @GetMapping("/dashboard")
    public String adminDashboard(
            @RequestParam(defaultValue = "all") String status,
            Model model,
            HttpSession session) {

        // 验证登录状态
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }

        // 业务逻辑
        List<Appointment> appointments = appointmentService.getAllAppointments(status);
        model.addAttribute("appointments", appointments);
        model.addAttribute("currentStatus", status);

        return "admin/dashboard";
    }

    /**
     * 管理员退出登录
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 清除会话
        return "redirect:/admin/login";
    }



    /**
     * 更新预约状态（审核操作）
     */
    @PostMapping("/appointments/update-status")
    public String updateAppointmentStatus(
            @RequestParam Long id,
            @RequestParam String status,
            HttpSession session) {

        // 验证登录状态
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }

        // 执行状态更新
        appointmentService.updateStatus(id, status);

        // 重定向回列表页（避免表单重复提交）
        return "redirect:/admin/dashboard";
    }
}
