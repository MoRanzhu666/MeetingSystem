package com.gp.mts.service;

import com.gp.mts.bean.Appointment;
import com.gp.mts.vo.AppointmentQueryVO;
import com.gp.mts.vo.AppointmentRegisterVO;
import java.util.List;
import java.util.Map;

public interface AppointmentService {
    Map<String, Object> register(AppointmentRegisterVO registerVO);
    Map<String, Object> query(AppointmentQueryVO queryVO);
    List<Appointment> getAllAppointments(String status);
    boolean updateStatus(Long id, String status);
}
