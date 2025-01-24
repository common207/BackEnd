package com.a207.smartlocker.service;

import com.a207.smartlocker.model.dto.StorageRequest;
import com.a207.smartlocker.model.dto.StorageResponse;
import com.a207.smartlocker.model.entity.Locker;

import java.util.List;

// LockerService.java
public interface LockerService {
    StorageResponse storeItem(StorageRequest request);
    List<Locker> getLockersByLocation(String locationName);
}
