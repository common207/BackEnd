package com.a207.smartlocker.serviceImpl;

import com.a207.smartlocker.exception.custom.NoAvailableRobotException;
import com.a207.smartlocker.exception.custom.NotFoundException;
import com.a207.smartlocker.model.dto.RobotTaskResponse;
import com.a207.smartlocker.model.entity.*;
import com.a207.smartlocker.repository.*;
import com.a207.smartlocker.service.RobotControlService;
import com.a207.smartlocker.service.RobotTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class RobotTaskServiceImpl implements RobotTaskService {
    private final LockerQueueRepository lockerQueueRepository;
    private final RobotRepository robotRepository;
    private final LockerRepository lockerRepository;
    private final LockerStatusRepository lockerStatusRepository;
    private final LockerUsageLogRepository lockerUsageLogRepository;
    private final RobotControlService robotControlService;
    private final UserRepository userRepository;

    @Override
    public RobotTaskResponse processNextTask() throws Exception {
        // 1. 가장 오래된 대기 중인 큐 작업 조회
        LockerQueue oldestTask = lockerQueueRepository.findFirstByOrderByQueueIdAsc()
                .orElseThrow(() -> new NotFoundException("대기 중인 작업이 없습니다."));

        // 2. 사용 가능한 로봇 선택 및 상태 변경
        Robot robot = robotRepository.findAndUpdateRobotStatus(1L, 2L)
                .orElseThrow(() -> new NoAvailableRobotException("사용 가능한 로봇이 없습니다."));

        // 3. 락커 조회
        Locker locker = oldestTask.getLocker();

        // 4. 로봇 작업 수행
        boolean robotResult = robotControlService.controlRobot(
                robot.getRobotId(),
                locker.getLockerId(),
                oldestTask.getRequestType().toLowerCase()
        );

        if (!robotResult) {
            throw new RuntimeException("Robot operation failed");
        }

        // 5. 락커 상태 업데이트 (수령 시에만)
        updateLockerStatus(locker, oldestTask.getRequestType());

        // 6. 사용 로그 업데이트
        updateLockerUsageLog(locker, robot, oldestTask.getRequestType());

        // 7. 로봇 상태를 대기 중으로 변경
        robotRepository.updateRobotStatus(robot.getRobotId(), 1L);

        // 8. 큐 작업 제거
        lockerQueueRepository.delete(oldestTask);

        // 9. 응답 생성
        return RobotTaskResponse.builder()
                .LockerId(locker.getLockerId())
                .success(true)
                .message("로봇 작업 완료")
                .build();
    }

    private void updateLockerStatus(Locker locker, String requestType) {
        LockerStatus status;
        if ("Retrieve".equalsIgnoreCase(requestType)) {
            status = lockerStatusRepository.findById(1L)
                    .orElseThrow(() -> new NotFoundException("LockerStatus not found"));
            locker.updateLockerStatus(status, null);
        }
        lockerRepository.save(locker);
    }

    private void updateLockerUsageLog(Locker locker, Robot robot, String requestType) {
        if ("Store".equalsIgnoreCase(requestType)) { // 보관 작업 시
            // LockerId가 일치하면서 StoreRobotId가 NULL인 레코드 찾기
            LockerUsageLog usageLog = lockerUsageLogRepository
                    .findFirstByLocker_LockerIdAndStoreRobotIdIsNull(locker.getLockerId())
                    .orElseThrow(() -> new NotFoundException("사용 중인 로그를 찾을 수 없음"));

            usageLog.setStoreRobotId(robot);
            lockerUsageLogRepository.save(usageLog);
        } else if ("Retrieve".equalsIgnoreCase(requestType)) { // 수령 작업 시
            // LockerId가 일치하면서 RetrieveRobotId가 NULL인 레코드 찾기
            LockerUsageLog usageLog = lockerUsageLogRepository
                    .findFirstByLocker_LockerIdAndRetrieveRobotIdIsNull(locker.getLockerId())
                    .orElseThrow(() -> new NotFoundException("사용 중인 로그를 찾을 수 없음"));

            usageLog.setRetrieveRobotId(robot);
            usageLog.setRetrieveTime(LocalDateTime.now());
            lockerUsageLogRepository.save(usageLog);
        }
    }
}