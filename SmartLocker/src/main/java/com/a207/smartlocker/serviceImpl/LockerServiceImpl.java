package com.a207.smartlocker.serviceImpl;

import com.a207.smartlocker.model.dto.StorageRequest;
import com.a207.smartlocker.model.dto.StorageResponse;
import com.a207.smartlocker.model.entity.User;
import com.a207.smartlocker.repository.UserRepository;
import com.a207.smartlocker.service.LockerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LockerServiceImpl implements LockerService {
    private final UserRepository userRepository;

    @Override
    public StorageResponse storeItem(StorageRequest request) {
        // 1. 사용자 확인 또는 생성
        User user = userRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseGet(() -> userRepository.save(User.builder()
                        .phoneNumber(request.getPhoneNumber())
                        .build()));
        return StorageResponse.builder()
                .lockerId(1234L)
                .tokenValue(123456L)
                .message("Storage request successful")
                .build();
    }
}
