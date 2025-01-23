package com.a207.smartlocker.serviceImpl;

import com.a207.smartlocker.model.dto.StorageRequest;
import com.a207.smartlocker.model.dto.StorageResponse;
import com.a207.smartlocker.model.entity.AccessToken;
import com.a207.smartlocker.model.entity.User;
import com.a207.smartlocker.repository.AccessTokenRepository;
import com.a207.smartlocker.repository.UserRepository;
import com.a207.smartlocker.service.LockerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class LockerServiceImpl implements LockerService {
    private final UserRepository userRepository;
    private final AccessTokenRepository accessTokenRepository;

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

        return StorageResponse.builder()
                .lockerId(1234L)
                .tokenValue(123456L)
                .message("Storage request successful")
                .build();
    }

    private int generateRandomToken() {
        Random random = new Random();
        return 100000 + random.nextInt(900000); // 6자리 난수 생성
    }
}
