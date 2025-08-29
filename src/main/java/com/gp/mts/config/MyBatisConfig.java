package com.gp.mts.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

// 扫描Mapper接口所在的包，自动生成实现类
@Configuration
@MapperScan("com.gp.mts.mapper") // 指定Mapper接口的包路径
public class MyBatisConfig {
    // 如需自定义MyBatis配置，可在此添加
}
