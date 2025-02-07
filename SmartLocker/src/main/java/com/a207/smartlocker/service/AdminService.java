package com.a207.smartlocker.service;

import com.a207.smartlocker.model.dto.AdminLoginRequest;
import com.a207.smartlocker.model.dto.AdminLoginResponse;
import com.a207.smartlocker.model.entity.Robot;

import java.util.List;

public interface AdminService {
    AdminLoginResponse login(AdminLoginRequest request);
    List<Robot> getAllRobots();
}
