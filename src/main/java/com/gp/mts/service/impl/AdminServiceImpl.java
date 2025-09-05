package com.gp.mts.service.impl;

import com.gp.mts.bean.po.Admin;
import com.gp.mts.mapper.AdminMapper;
import com.gp.mts.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    // 注入Mapper接口（由MyBatis自动生成实现类）
    private final AdminMapper adminMapper;

    @Override
    public boolean login(String username, String password) {
        Admin admin = adminMapper.selectByUsername(username);
        // 生产环境需使用密码加密算法验证（如BCrypt）
        return admin != null && admin.getPassword().equals(password);
    }
}
