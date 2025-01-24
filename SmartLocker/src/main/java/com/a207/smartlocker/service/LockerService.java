package com.a207.smartlocker.service;

import com.a207.smartlocker.model.dto.RetrieveRequest;
import com.a207.smartlocker.model.dto.RetrieveResponse;

public interface LockerService {
    RetrieveResponse retrieveItem(RetrieveRequest request) throws Exception;
}
