package com.a207.smartlocker.service;

import com.a207.smartlocker.model.dto.StorageRequest;
import com.a207.smartlocker.model.dto.StorageResponse;

// LockerService.java
public interface LockerService {
    StorageResponse storeItem(StorageRequest request);
}
