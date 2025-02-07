package com.a207.smartlocker.controller;

import com.a207.smartlocker.model.dto.AdminLoginRequest;
import com.a207.smartlocker.model.dto.AdminLoginResponse;
import com.a207.smartlocker.model.entity.Robot;
import com.a207.smartlocker.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<AdminLoginResponse> login(@RequestBody AdminLoginRequest request) {
        AdminLoginResponse response = adminService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/robots")
    public ResponseEntity<List<Robot>> getRobots() {
        List<Robot> robots = adminService.getAllRobots();
        return ResponseEntity.ok(robots);
    }
}