package com.gp.mts.mapper;

import com.gp.mts.bean.Appointment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.time.LocalDate;
import java.util.List;

@Mapper
public interface AppointmentMapper {
    // 检查重复预约（计数）
    int countByPrisonerIdAndVisitorIdAndDate(
            @Param("prisonerId") String prisonerId,
            @Param("visitorId") String visitorId,
            @Param("date") LocalDate date);

    // 新增预约
    void insert(Appointment appointment);

    // 多条件查询
    List<Appointment> selectByVisitorIdAndPrisonerId(
            @Param("visitorId") String visitorId,
            @Param("prisonerId") String prisonerId);

    // 按会见人ID查询
    List<Appointment> selectByVisitorId(@Param("visitorId") String visitorId);

    // 按服刑人员ID查询
    List<Appointment> selectByPrisonerId(@Param("prisonerId") String prisonerId);

    // 查询所有（按日期倒序）
    List<Appointment> selectAllOrderByDateDesc();

    // 按状态查询
    List<Appointment> selectByStatusOrderByDateDesc(@Param("status") String status);

    // 按ID查询
    Appointment selectById(@Param("id") Long id);

    // 更新状态
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
