package com.gp.mts.vo;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 预约查询请求参数
 */
@Data
public class AppointmentQueryVO {

    @Pattern(regexp = "^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]?$",
             message = "身份证号格式不正确")
    private String visitorId; // 会见人身份证号（可选）

    private String prisonerId; // 服刑人员编号（可选）
}
