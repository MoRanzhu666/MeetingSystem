package com.gp.mts.service.impl;

import com.gp.mts.bean.po.Categories;
import com.gp.mts.mapper.CategoriesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriesService {
    @Autowired
    private CategoriesMapper categoriesMapper;

    public List<Categories> getCategories(String category, String describeInfo) {
        return categoriesMapper.getCategories(category, describeInfo);
    }

    public List<String> calculateFeatuer7DayList() {
        List<String> dates = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (int i = 1; i <= 7; i++) { // 未来7天的可选日期
            dates.add(today.plusDays(i).format(DateTimeFormatter.ISO_LOCAL_DATE));
        }

        return dates;
    }
}
