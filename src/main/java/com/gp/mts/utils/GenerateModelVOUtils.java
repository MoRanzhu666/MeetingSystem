package com.gp.mts.utils;

import org.springframework.ui.Model;

import com.gp.mts.vo.AdminLoginVO;
import com.gp.mts.vo.AppointmentQueryVO;
import com.gp.mts.vo.AppointmentRegisterVO;

public class GenerateModelVOUtils {

    public static void generateAdminLoginVO(Model model){
        model.addAttribute("loginVO", new AdminLoginVO());
    }

    
    public static void generateAppointmentQueryVO(Model model){
        model.addAttribute("queryVO", new AppointmentQueryVO());
    }
    
    public static void generateAppointmentRegisterVO(Model model){
        model.addAttribute("registerVO", new AppointmentRegisterVO());
    }

    public static void generateErrorMsg(Model model, Object msg){
        model.addAttribute("errorMsg", msg);
    }
}
