package com.a207.smartlocker.serviceImpl;

import com.a207.smartlocker.model.dto.RetrieveRequest;
import com.a207.smartlocker.model.dto.RetrieveResponse;
import com.a207.smartlocker.model.entity.Locker;
import com.a207.smartlocker.model.entity.AccessToken;
import com.a207.smartlocker.repository.LockerRepository;
import com.a207.smartlocker.repository.AccessTokenRepository;
import com.a207.smartlocker.service.LockerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LockerServiceImpl implements LockerService {

    @Autowired
    private LockerRepository lockerRepository;

    @Autowired
    private AccessTokenRepository accessTokenRepository;

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

        return RetrieveResponse.builder()
                .lockerId(request.getLockerId())
                .tokenValue(request.getTokenValue())
                .message(request.getLockerId() + "번 보관함의 수령 요청")
                .build();
    }
}
