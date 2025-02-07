package com.a207.smartlocker.service;

import com.a207.smartlocker.model.dto.AdminLoginRequest;
import com.a207.smartlocker.model.dto.AdminLoginResponse;

public interface AdminService {
    AdminLoginResponse login(AdminLoginRequest request);
}
