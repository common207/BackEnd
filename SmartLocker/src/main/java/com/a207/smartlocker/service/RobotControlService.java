package com.a207.smartlocker.service;

import com.a207.smartlocker.exception.custom.RobotControlException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class RobotControlService {
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String ROBOT_SERVER_URL = "http://localhost:5001";

    public  boolean controlRobot(Long robotId, Long lockerId, String action) {
        Map<String, Object> request = new HashMap<>();
        request.put("robot_Id", robotId);
        request.put("locker_Id", lockerId);
        request.put("action", action);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(ROBOT_SERVER_URL, request, Map.class);
            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception e) {
            throw new RobotControlException("로봇 작동 중 예외가 발생하였습니다 : " + e.getMessage());
        }
    }
}
