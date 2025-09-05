package com.gp.mts.mapper;

import com.gp.mts.bean.po.Categories;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoriesMapper {
    List<Categories> getCategories(String category,String describeInfo);
}
