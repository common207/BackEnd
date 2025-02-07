package com.a207.smartlocker.service;

import com.a207.smartlocker.model.dto.AdminLoginRequest;
import com.a207.smartlocker.model.dto.AdminLoginResponse;
import com.a207.smartlocker.model.dto.RobotResponse;
import com.a207.smartlocker.model.dto.UserUsageResponse;

import java.util.List;

public interface AdminService {
    AdminLoginResponse login(AdminLoginRequest request);
    List<RobotResponse> getAllRobots();
    List<UserUsageResponse> getUserUsageStatistics();
}
