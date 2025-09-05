package com.gp.mts.bean.po;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // 主键ID
    
    @Column(name = "prisoner_id", nullable = false)
    private String prisonerId;          // 服刑人员编号
    
    @Column(name = "prisoner_name", nullable = false)
    private String prisonerName;        // 服刑人员姓名
    
    @Column(name = "visitor_name", nullable = false)
    private String visitorName;         // 会见人姓名
    
    @Column(name = "visitor_id", nullable = false)
    private String visitorId;           // 会见人身份证号
    
    @Column(nullable = false)
    private String relation;            // 与服刑人员关系
    
    @Column(nullable = false)
    private String phone;               // 会见人联系电话
    
    @Column(name = "appointment_date", nullable = false)
    private LocalDate appointmentDate;  // 预约日期
    
    @Column(name = "appointment_time", nullable = false)
    private String appointmentTime;     // 预约时段
    
    @Column(nullable = false)
    private String status;              // 预约状态（pending/approved/rejected等）
    
    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime;   // 创建时间
}
