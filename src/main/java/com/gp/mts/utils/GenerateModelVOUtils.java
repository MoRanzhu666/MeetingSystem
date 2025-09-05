package com.gp.mts.utils;

import com.gp.mts.bean.Enum.MessageEnum;
import com.gp.mts.bean.Enum.VOEnum;
import org.springframework.ui.Model;

import com.gp.mts.vo.AdminLoginVO;
import com.gp.mts.vo.AppointmentQueryVO;
import com.gp.mts.vo.AppointmentRegisterVO;

public class GenerateModelVOUtils {

    public static void generateAdminLoginVO(Model model){
        model.addAttribute(VOEnum.ADMIN_LOGIN_VO.getVoName(), new AdminLoginVO());
    }

    public static void generateAppointmentQueryVO(Model model){
        model.addAttribute(VOEnum.QUERY_VO.getVoName(), new AppointmentQueryVO());
    }
    public static void generateAppointmentQueryVO(Model model, AppointmentQueryVO queryVO) {
        model.addAttribute(VOEnum.QUERY_VO.getVoName(), new AppointmentQueryVO());
    }
    public static void generateAppointmentRegisterVO(Model model){
        model.addAttribute(VOEnum.REGISTER_VO.getVoName(), new AppointmentRegisterVO());
    }


    public static void generateErrorMsg(Model model, Object msg){
        model.addAttribute(MessageEnum.ERROR.getMessageName(), msg);
        model.addAttribute(MessageEnum.IS_SUCCESS.getMessageName(), false);
    }

    public static void generateSuccessMsg(Model model, Object msg){
        model.addAttribute(MessageEnum.IS_SUCCESS.getMessageName(), true);
    }

    public static void generateGlobalMsg(Model model, String msg) {
        model.addAttribute(MessageEnum.Global.getMessageName(), msg);
    }
}
