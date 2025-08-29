package com.gp.mts.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 预约登记请求参数
 */
@Data
public class AppointmentRegisterVO {

    @NotBlank(message = "服刑人员编号不能为空")
    private String prisonerId; // 服刑人员编号

    @NotBlank(message = "服刑人员姓名不能为空")
    private String prisonerName; // 服刑人员姓名

    @NotBlank(message = "会见人姓名不能为空")
    private String visitorName; // 会见人姓名

    @NotBlank(message = "会见人身份证号不能为空")
    @Pattern(regexp = "^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$",
             message = "身份证号格式不正确")
    private String visitorId; // 会见人身份证号

    @NotBlank(message = "与服刑人员关系不能为空")
    private String relation; // 与服刑人员关系

    @NotBlank(message = "联系电话不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone; // 联系电话

    @NotBlank(message = "预约日期不能为空")
    private String appointmentDate; // 预约日期（格式：yyyy-MM-dd）

    @NotBlank(message = "预约时段不能为空")
    private String appointmentTime; // 预约时段（如：09:00-10:00）
}
