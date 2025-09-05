package com.gp.mts.service.impl;

import com.gp.mts.bean.Enum.CategoriesEnum;
import com.gp.mts.utils.GenerateModelVOUtils;
import com.gp.mts.vo.AppointmentQueryVO;
import com.gp.mts.vo.AppointmentRegisterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PageService {
    @Autowired
    private CategoriesService categoriesService;


    public void initRegisterPageModel(Model model) {
        // 必须添加：初始化表单绑定对象
        GenerateModelVOUtils.generateAppointmentRegisterVO(model);

        model.addAttribute("dateList", categoriesService.calculateFeatuer7DayList());
        model.addAttribute("timeList", categoriesService.getCategories(CategoriesEnum.TIME_SCOPE_ALL.getCategory(), CategoriesEnum.TIME_SCOPE_ALL.getDescribeInfo()));
        model.addAttribute("relationList", categoriesService.getCategories(CategoriesEnum.RELATION_ALL.getCategory(), CategoriesEnum.RELATION_ALL.getDescribeInfo()));
    }

    public void initQueryPageModel(Model model) {
        GenerateModelVOUtils.generateAppointmentQueryVO(model);
    }
}
