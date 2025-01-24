package com.a207.smartlocker.serviceImpl;

import com.a207.smartlocker.exception.NotFoundException;
import com.a207.smartlocker.model.dto.LockerDto;
import com.a207.smartlocker.model.dto.StorageRequest;
import com.a207.smartlocker.model.dto.StorageResponse;
import com.a207.smartlocker.model.entity.*;
import com.a207.smartlocker.repository.*;
import com.a207.smartlocker.repository.LockerStatusRepository;
import com.a207.smartlocker.service.LockerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LockerServiceImpl implements LockerService {
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
