package com.a207.smartlocker.serviceImpl;


import com.a207.smartlocker.model.dto.RetrieveRequest;
import com.a207.smartlocker.model.dto.RetrieveResponse;
import com.a207.smartlocker.model.entity.Locker;
import com.a207.smartlocker.model.entity.AccessToken;
import com.a207.smartlocker.model.entity.LockerStatus;
import com.a207.smartlocker.model.entity.LockerUsageLog;
import com.a207.smartlocker.model.entity.User;
import com.a207.smartlocker.model.entity.RobotStatus;
import com.a207.smartlocker.model.entity.Robot;
import com.a207.smartlocker.repository.LockerRepository;
import com.a207.smartlocker.repository.AccessTokenRepository;
import com.a207.smartlocker.repository.LockerStatusRepository;
import com.a207.smartlocker.repository.LockerUsageLogRepository;
import com.a207.smartlocker.repository.UserRepository;
import com.a207.smartlocker.repository.RobotRepository;
import com.a207.smartlocker.repository.RobotStatusRepository;
import com.a207.smartlocker.exception.NotFoundException;
import com.a207.smartlocker.model.dto.StorageRequest;
import com.a207.smartlocker.model.dto.StorageResponse;
import com.a207.smartlocker.service.LockerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class LockerServiceImpl implements LockerService {
    private final UserRepository userRepository;
    private final AccessTokenRepository accessTokenRepository;
    private final LockerRepository lockerRepository;
    private final LockerStatusRepository lockerStatusRepository;
    private final RobotRepository robotRepository;
    private final LockerUsageLogRepository lockerUsageLogRepository;
    private final RobotStatusRepository robotStatusRepository;

    @Override
    public StorageResponse storeItem(StorageRequest request) {
        // 1. 사용 가능한 로봇 찾기
        Robot storeRobot = robotRepository.findAndUpdateRobotStatus(1L, 2L)
                .orElseThrow(() -> new RuntimeException("No available robot"));

        // 2. 사용자 확인/생성
        User user = userRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseGet(() -> userRepository.save(User.builder()
                        .phoneNumber(request.getPhoneNumber())
                        .build()));

        // 3. 6자리 토큰 생성
        int tokenValue = generateRandomToken();
        AccessToken accessToken = accessTokenRepository.save(AccessToken.builder()
                .tokenValue((long) tokenValue)
                .build());

        // 4. 락커 상태 업데이트
        Locker locker = lockerRepository.findByLockerId(request.getLockerID())
                .orElseThrow(() -> new NotFoundException("Locker not found"));

        LockerStatus status = lockerStatusRepository.findById(2L)
                .orElseThrow(() -> new NotFoundException("LockerStatus not found"));

        locker.updateStatus(status);
        locker.updateToken(accessToken);
        lockerRepository.save(locker);

        // 5. 사용 로그 생성
        LockerUsageLog usageLog = LockerUsageLog.builder()
                .locker(locker)
                .user(user)
                .storeTime(LocalDateTime.now())
                .storeRobotId(storeRobot)
                .build();
        lockerUsageLogRepository.save(usageLog);

        // 6. 결과 리턴
        return StorageResponse.builder()
                .lockerId(locker.getLockerId())
                .tokenValue(accessToken.getTokenValue())
                .message("보관 요청 성공")
                .build();
    }

    @Override
    public RetrieveResponse retrieveItem(RetrieveRequest request) throws Exception {
        // 1. 사용 가능한 로봇 찾기
        Robot retrieveRobot = robotRepository.findAndUpdateRobotStatus(1L, 2L)
                .orElseThrow(() -> new RuntimeException("No available robot"));

        // 2. 락커 조회
        Locker locker = lockerRepository.findById(request.getLockerId())
                .orElseThrow(() -> new Exception("해당 락커를 찾을 수 없음: " + request.getLockerId()));

        // 3. 토큰 확인
        Long tokenId = locker.getTokenId();
        if (tokenId == null) {
            throw new Exception("락커에 토큰이 없음: " + request.getLockerId());
        }

        AccessToken accessToken = accessTokenRepository.findById(tokenId)
                .orElseThrow(() -> new Exception("토큰을 찾을 수 없음: " + tokenId));

        if (accessToken.getTokenValue() != request.getTokenValue()) {
            throw new Exception("토큰 불일치: " + request.getLockerId());
        }

        // 4. 락커 상태 업데이트
        locker.setTokenId(null);
        lockerRepository.save(locker);

        LockerStatus lockerStatus = lockerStatusRepository.findById(1L)
                .orElseThrow(() -> new Exception("락커 상태를 찾을 수 없음: " + request.getLockerId()));
        locker.setLockerStatus(lockerStatus);
        lockerStatusRepository.save(lockerStatus);

        // 5. 사용 로그 업데이트
        LockerUsageLog usageLog = lockerUsageLogRepository.findFirstByLocker_LockerIdAndRetrieveTimeIsNull(request.getLockerId())
                .orElseThrow(() -> new Exception("사용 중인 로그를 찾을 수 없음: " + request.getLockerId()));
        usageLog.setRetrieveRobotId(retrieveRobot);
        usageLog.setRetrieveTime(LocalDateTime.now());
        lockerUsageLogRepository.save(usageLog);

        // 6. 결과 리턴
        return RetrieveResponse.builder()
                .lockerId(request.getLockerId())
                .tokenValue(request.getTokenValue())
                .message(request.getLockerId() + "번 보관함의 수령 요청 완료")
                .build();
    }

    @Override
    public List<Locker> getLockersByLocation(String locationName) {
        return lockerRepository.findLockersByLocationName(locationName);
    }

    private int generateRandomToken() {
        return 100000 + new Random().nextInt(900000);
    }
}