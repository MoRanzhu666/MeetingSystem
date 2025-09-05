package com.gp.mts.bean.po;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "admins")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // 主键ID
    
    @Column(nullable = false, unique = true)
    private String username;            // 管理员用户名
    
    @Column(nullable = false)
    private String password;            // 管理员密码（建议加密存储）
    
    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime;   // 创建时间
    
    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;   // 更新时间
}
