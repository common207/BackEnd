package com.a207.smartlocker.serviceImpl;

import com.a207.smartlocker.exception.custom.RobotControlException;
import com.a207.smartlocker.model.dto.RobotControlRequest;
import com.a207.smartlocker.service.RobotControlService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpStatusCodeException;

@Service
public class RobotControlServiceImpl implements RobotControlService {
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String ROBOT_SERVER_URL = "http://70.12.245.25:5001/rasp";

    public boolean controlRobot(Long robotId, Long lockerId, String action) {
        try {
            System.out.println("실행 환경 정보:");
            System.out.println("Java Version: " + System.getProperty("java.version"));
            System.out.println("OS Name: " + System.getProperty("os.name"));
            System.out.println("User Directory: " + System.getProperty("user.dir"));
            ObjectMapper objectMapper = new ObjectMapper();

            RobotControlRequest request = RobotControlRequest.builder()
                    .robot_Id(robotId)
                    .locker_Id(lockerId)
                    .action(action)
                    .build();

            // 객체를 JSON 문자열로 직렬화
            String jsonRequest = objectMapper.writeValueAsString(request);
            System.out.println("JSON Request: " + jsonRequest);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> requestEntity = new HttpEntity<>(jsonRequest, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                    ROBOT_SERVER_URL,
                    requestEntity,
                    String.class
            );

            return "done".equals(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RobotControlException("로봇 작동 중 예외가 발생하였습니다 : " + e.getMessage());
        }
    }
}