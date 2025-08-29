package com.gp.mts.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 管理员登录请求参数
 */
@Data
public class AdminLoginVO {

    @NotBlank(message = "用户名不能为空")
    private String username; // 管理员用户名

    @NotBlank(message = "密码不能为空")
    private String password; // 管理员密码
}
