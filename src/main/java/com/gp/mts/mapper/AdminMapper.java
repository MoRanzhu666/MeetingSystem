package com.gp.mts.mapper;

import com.gp.mts.bean.po.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminMapper {
    // 根据用户名查询管理员
    Admin selectByUsername(@Param("username") String username);
}
