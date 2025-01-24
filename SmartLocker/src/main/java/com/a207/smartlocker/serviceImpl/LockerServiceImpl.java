package com.a207.smartlocker.serviceImpl;


import com.a207.smartlocker.model.dto.RetrieveRequest;
import com.a207.smartlocker.model.dto.RetrieveResponse;
import com.a207.smartlocker.model.entity.Locker;
import com.a207.smartlocker.model.entity.AccessToken;
import com.a207.smartlocker.model.entity.LockerStatus;
import com.a207.smartlocker.model.entity.LockerUsageLog;
import com.a207.smartlocker.repository.LockerRepository;
import com.a207.smartlocker.repository.AccessTokenRepository;
import com.a207.smartlocker.repository.LockerStatusRepository;
import com.a207.smartlocker.repository.LockerUsageLogRepository;

import com.a207.smartlocker.exception.NotFoundException;
import com.a207.smartlocker.model.dto.LockerDto;
import com.a207.smartlocker.model.dto.StorageRequest;
import com.a207.smartlocker.model.dto.StorageResponse;
import com.a207.smartlocker.model.entity.*;
import com.a207.smartlocker.repository.*;
import com.a207.smartlocker.repository.LockerStatusRepository;
import com.a207.smartlocker.service.LockerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class LockerServiceImpl implements LockerService {

    @Autowired
    private LockerRepository lockerRepository;

    @Autowired
    private AccessTokenRepository accessTokenRepository;

    @Autowired
    private LockerStatusRepository lockerStatusRepository;

    @Autowired
    private LockerUsageLogRepository lockerUsageLogRepository;

    @Override
    public RetrieveResponse retrieveItem(RetrieveRequest request) throws Exception {
        // Step 1: Find the locker by ID
        Locker locker = lockerRepository.findById(request.getLockerId()).orElseThrow(() ->
                new Exception("Locker not found for ID: " + request.getLockerId()));

        Long tokenId = locker.getTokenId();
        if (tokenId == null) {
            throw new Exception("No token associated with locker ID: " + request.getLockerId());
        }

        // Step 2: Verify the token value matches
        AccessToken accessToken = accessTokenRepository.findById(tokenId).orElseThrow(() ->
                new Exception("Token not found for ID: " + tokenId));

        if (accessToken.getTokenValue() != request.getTokenValue()) {
            throw new Exception("Token value mismatch for locker ID: " + request.getLockerId());
        }

        // Step 3: Perform retrieval logic (e.g., mark as retrieved, update logs, etc.)
        locker.setTokenId(null); // Clear the token association
        lockerRepository.save(locker);

        // Step 4.1: Update locker_status to available (0)
        LockerStatus lockerStatus = lockerStatusRepository.findById(request.getLockerId()).orElseThrow(() ->
                new Exception("Locker status not found for ID: " + request.getLockerId()));
        lockerStatus.setLockerStatus(String.valueOf(0)); // Set to available
        lockerStatusRepository.save(lockerStatus);

        // Step 4.2: Update locker_usage_log with retrieve_robot_id and retrieve_time
        LockerUsageLog lockerUsageLog = lockerUsageLogRepository.findFirstByLockerIdAndRetrieveTimeIsNull(request.getLockerId())
                .orElseThrow(() -> new Exception("No active usage log found for locker ID: " + request.getLockerId()));
        lockerUsageLog.setRetrieveRobotId(request.getRetrieveRobotId());
        lockerUsageLog.setRetrieveTime(LocalDateTime.now());
        lockerUsageLogRepository.save(lockerUsageLog);

        return RetrieveResponse.builder()
                .lockerId(request.getLockerId())
                .tokenValue(request.getTokenValue())
                .message(request.getLockerId() + "번 보관함의 수령 요청 완료")
      }
    private final UserRepository userRepository;
    private final AccessTokenRepository accessTokenRepository;
    private final LockerRepository lockerRepository;
    private final LockerStatusRepository lockerStatusRepository;
    private final RobotRepository robotRepository;
    private final LockerUsageLogRepository usageLogRepository;
    private final RobotStatusRepository robotStatusRepository;

    @Override
    public StorageResponse storeItem(StorageRequest request) {
        // 1. 사용자 확인 또는 생성
        User user = userRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseGet(() -> userRepository.save(User.builder()
                        .phoneNumber(request.getPhoneNumber())
                        .build()));

        // 2. 랜덤 토큰 생성 (6자리)
        int tokenValue = generateRandomToken();

        // 3. Access Token 생성
        AccessToken accessToken = accessTokenRepository.save(AccessToken.builder()
                .tokenValue((long) tokenValue)
                .build());

        // 4. Locker 상태 업데이트
        Locker locker = lockerRepository.findByLockerId(request.getLockerID())
                .orElseThrow(() -> new NotFoundException("Locker not found"));

        LockerStatus status = lockerStatusRepository.findById(1L)
                        .orElseThrow(() -> new NotFoundException("LockerStatus not found"));

        locker.updateStatus(status); // 사용중 상태로 변경
        locker.updateToken(accessToken);
        lockerRepository.save(locker);

        // 5. 사용 가능한 Robot 찾기
        RobotStatus robotStatus = robotStatusRepository.findById(1L)
                .orElseThrow(() -> new NotFoundException("RobotStatus not found"));

        Robot robot = robotRepository.findByRobotStatus(robotStatus)
                .orElseThrow(() -> new RuntimeException("No available robot"));

        // 6. 사용 로그 생성
        LockerUsageLog usageLog = LockerUsageLog.builder()
                .locker(locker)
                .user(user)
                .storeTime(LocalDateTime.now())
                .storeRobot(robot)
                .build();

        usageLogRepository.save(usageLog);

        return StorageResponse.builder()
                .lockerId(locker.getLockerId())
                .tokenValue(accessToken.getTokenValue())
                .message("Storage request successful")
                .build();
    }

    @Override
    public List<Locker> getLockersByLocation(String locationName) {
        return lockerRepository.findLockersByLocationName(locationName);
    }

    private int generateRandomToken() {
        Random random = new Random();
        return 100000 + random.nextInt(900000); // 6자리 난수 생성
    }
}
