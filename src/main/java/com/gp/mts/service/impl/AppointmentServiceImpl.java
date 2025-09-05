package com.gp.mts.service.impl;

import com.gp.mts.bean.Enum.MessageEnum;
import com.gp.mts.bean.po.Appointment;
import com.gp.mts.mapper.AppointmentMapper;
import com.gp.mts.service.AppointmentService;
import com.gp.mts.utils.GenerateModelVOUtils;
import com.gp.mts.utils.PageUtils;
import com.gp.mts.vo.AppointmentQueryVO;
import com.gp.mts.vo.AppointmentRegisterVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    // 注入MyBatis的Mapper接口
    private final AppointmentMapper appointmentMapper;

    /**
     * 处理预约登记业务
     * 1. 检查是否重复预约（同一服刑人员+会见人+日期）
     * 2. 转换VO为实体并保存
     */
    @Override
    @Transactional // 确保数据一致性
    public Map<String, Object> register(AppointmentRegisterVO registerVO) {
        Map<String, Object> result = new HashMap<>();

        // 解析预约日期（字符串转LocalDate）
        LocalDate appointmentDate = LocalDate.parse(registerVO.getAppointmentDate());

        // 检查是否重复预约（通过计数判断）
        int duplicateCount = appointmentMapper.countByPrisonerIdAndVisitorIdAndDate(
                registerVO.getPrisonerId(),
                registerVO.getVisitorId(),
                appointmentDate
        );

        if (duplicateCount > 0) {
            result.put("success", false);
            result.put("message", "您已为该服刑人员预约过当天的会见，请更换日期");
            return result;
        }

        // 转换VO为实体对象
        Appointment appointment = new Appointment();
        appointment.setPrisonerId(registerVO.getPrisonerId());
        appointment.setPrisonerName(registerVO.getPrisonerName());
        appointment.setVisitorName(registerVO.getVisitorName());
        appointment.setVisitorId(registerVO.getVisitorId());
        appointment.setRelation(registerVO.getRelation());
        appointment.setPhone(registerVO.getPhone());
        appointment.setAppointmentDate(appointmentDate);
        appointment.setAppointmentTime(registerVO.getAppointmentTime());
        appointment.setStatus("pending"); // 默认为"待审核"状态

        // 保存到数据库
        appointmentMapper.insert(appointment);

        result.put("success", true);
        result.put("message", "预约提交成功，请等待审核结果");
        result.put("appointmentId", appointment.getId()); // 返回生成的预约ID
        return result;
    }

    /**
     * 处理预约查询业务
     * 支持多条件组合查询（会见人ID/服刑人员ID）
     */
    @Override
    public Map<String, Object> query(AppointmentQueryVO queryVO) {
        Map<String, Object> result = new HashMap<>();
        String visitorId = queryVO.getVisitorId();
        String prisonerId = queryVO.getPrisonerId();
        List<Appointment> appointments;

        // 按不同条件查询（至少需要一个查询条件）
        if (isValidParam(visitorId) && isValidParam(prisonerId)) {
            // 条件1：同时提供会见人ID和服刑人员ID
            appointments = appointmentMapper.selectByVisitorIdAndPrisonerId(visitorId, prisonerId);
        } else if (isValidParam(visitorId)) {
            // 条件2：仅提供会见人ID
            appointments = appointmentMapper.selectByVisitorId(visitorId);
        } else if (isValidParam(prisonerId)) {
            // 条件3：仅提供服刑人员ID
            appointments = appointmentMapper.selectByPrisonerId(prisonerId);
        } else {
            // 无查询条件
            result.put("success", false);
            result.put("message", "请输入会见人身份证号或服刑人员编号");
            return result;
        }

        // 处理查询结果
        if (appointments.isEmpty()) {
            result.put("success", false);
            result.put("message", "未查询到相关预约记录");
        } else {
            result.put("success", true);
            result.put("data", appointments); // 返回查询到的预约列表
            result.put("count", appointments.size()); // 记录总数
        }
        return result;
    }

    /**
     * 获取所有预约记录（支持按状态筛选）
     * @param status 状态筛选条件（all：所有状态；其他值：指定状态）
     */
    @Override
    public List<Appointment> getAllAppointments(String status) {
        if ("all".equals(status) || status == null || status.isEmpty()) {
            // 查询所有预约（按日期倒序，最新的在前）
            return appointmentMapper.selectAllOrderByDateDesc();
        } else {
            // 按指定状态查询
            return appointmentMapper.selectByStatusOrderByDateDesc(status);
        }
    }

    /**
     * 更新预约状态（审核通过/拒绝/取消等）
     * @param id 预约ID
     * @param status 新状态（approved/rejected/canceled/completed）
     */
    @Override
    @Transactional
    public boolean updateStatus(Long id, String status) {
        // 校验参数合法性
        if (id == null || !isValidStatus(status)) {
            return false;
        }

        // 先查询预约是否存在
        Appointment appointment = appointmentMapper.selectById(id);
        if (appointment == null) {
            return false; // 预约记录不存在
        }

        // 执行更新操作（返回影响行数）
        int affectedRows = appointmentMapper.updateStatus(id, status);
        return affectedRows > 0;
    }

    // 辅助方法：校验字符串参数是否有效（非null且非空）
    private boolean isValidParam(String param) {
        return param != null && !param.trim().isEmpty();
    }

    // 辅助方法：校验状态是否合法
    private boolean isValidStatus(String status) {
        if (status == null) {
            return false;
        }
        // 合法状态列表
        return "pending".equals(status)
                || "approved".equals(status)
                || "rejected".equals(status)
                || "completed".equals(status)
                || "canceled".equals(status);
    }

    public String handleOperationResult(Model model, PageService pageService, Map<String, Object> result) {
        // 处理业务结果
        if ((boolean) result.get("success")) {
            // 预约成功，携带结果跳转到成功页
            GenerateModelVOUtils.generateSuccessMsg(model, pageService);
            GenerateModelVOUtils.generateGlobalMsg(model, result.get("message").toString());
            model.addAttribute("appointmentId", result.get("appointmentId"));

            return PageUtils.toRegisterPage(model, pageService); // 预约成功页
        } else {
            // 预约失败，返回原页面显示错误
            GenerateModelVOUtils.generateAppointmentRegisterVO(model);
            GenerateModelVOUtils.generateErrorMsg(model, result.get("message"));
            return PageUtils.toRegisterPage(model, pageService);
        }
    }
}
