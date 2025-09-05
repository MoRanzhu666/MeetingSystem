package com.gp.mts.controller;

import com.gp.mts.service.impl.AppointmentServiceImpl;
import com.gp.mts.service.impl.PageService;
import com.gp.mts.utils.GenerateModelVOUtils;
import com.gp.mts.utils.PageUtils;
import com.gp.mts.vo.AppointmentQueryVO;
import com.gp.mts.vo.AppointmentRegisterVO;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * 预约业务控制器（处理表单提交和数据渲染）
 */
@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentServiceImpl appointmentServiceImpl;
    @Autowired
    private PageService pageService;

    /**
     * 处理预约登记表单提交
     */
    @PostMapping("/register")
    @Transactional(rollbackFor = Exception.class)
    public String handleRegister(
            // @Valid @RequestBody(required = false) AppointmentRegisterVO registerVO,
            @Valid AppointmentRegisterVO registerVO,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            GenerateModelVOUtils.generateErrorMsg(model, "表单填写有误，请检查后重试");
            GenerateModelVOUtils.generateGlobalMsg(model, "表单填写有误，请检查后重试");
            return PageUtils.toRegisterPage(model, pageService); // 回到预约登记页
        }
        Map<String, Object> result = appointmentServiceImpl.register(registerVO);

        return appointmentServiceImpl.handleOperationResult(model, pageService,  result);
    }

    /**
     * 处理预约查询表单提交
     */
    @PostMapping("/query")
    @Transactional(rollbackFor = Exception.class)
    public String handleQuery(
            // @RequestBody(required = false) AppointmentQueryVO queryVO,
            AppointmentQueryVO queryVO,
            Model model) {
        // 调用服务层查询
        Map<String, Object> result = appointmentServiceImpl.query(queryVO);
        System.out.println("result"+result);
        // 绑定查询条件回显（方便用户修改）
//        model.addAttribute("queryVO", queryVO);

        // 处理查询结果
        if ((boolean) result.get("success")) {
            model.addAttribute("appointments", result.get("data"));
            model.addAttribute("count", result.get("count"));
            System.out.println("result"+result);
        } else {
            model.addAttribute("errorMsg", result.get("message"));
        }

//        return "query"; // 回到查询页展示结果
        return PageUtils.toQueryPage(model, queryVO);
    }
}
