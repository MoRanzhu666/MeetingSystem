package com.gp.mts.utils;

import com.gp.mts.bean.Enum.PageEnum;
import com.gp.mts.service.impl.AppointmentServiceImpl;
import com.gp.mts.service.impl.PageService;
import com.gp.mts.vo.AppointmentQueryVO;
import org.springframework.ui.Model;

public class PageUtils {
    public static String toRegisterPage(Model model) {
        GenerateModelVOUtils.generateAppointmentRegisterVO(model);
        return PageEnum.REGISTER.getPageName();
    }

    public static String toRegisterPage(Model model, PageService pageService) {
        GenerateModelVOUtils.generateAppointmentRegisterVO(model);
        pageService.initRegisterPageModel(model);
        return PageEnum.REGISTER.getPageName();
    }

    public static String toQueryPage(Model model) {
        GenerateModelVOUtils.generateAppointmentQueryVO(model);
        return PageEnum.QUERY.getPageName();
    }

    public static String toQueryPage(Model model, AppointmentQueryVO queryVO) {
        GenerateModelVOUtils.generateAppointmentQueryVO(model, queryVO);
        return PageEnum.QUERY.getPageName();
    }

    public static String toAdminLogin(Model model) {
        GenerateModelVOUtils.generateAdminLoginVO(model);
        return PageEnum.ADMIN_LOGIN.getPageName();
    }


    public static String toAdminDashboard(Model model) {
        return PageEnum.ADMIN_DASHBOARD.getPageName();
    }

    public static String RedirectAdminLogin() {
        return "redirect:/"+PageEnum.ADMIN_LOGIN.getPageName();
    }

    public static String RedirectAdminDashBoard() {
        return "redirect:/"+ PageEnum.ADMIN_DASHBOARD.getPageName();
    }


}
