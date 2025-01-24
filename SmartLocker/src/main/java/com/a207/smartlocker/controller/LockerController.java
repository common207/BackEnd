// LockerController.java
package com.a207.smartlocker.controller;

import com.a207.smartlocker.model.dto.RetrieveRequest;
import com.a207.smartlocker.model.dto.RetrieveResponse;
import com.a207.smartlocker.model.dto.StorageRequest;
import com.a207.smartlocker.model.dto.StorageResponse;
import com.a207.smartlocker.model.entity.Locker;
import com.a207.smartlocker.service.LockerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/locker")
public class LockerController {

    @Autowired
    private LockerService lockerService;

    @PostMapping("/store")
    public ResponseEntity<StorageResponse> store(@RequestBody StorageRequest request) {
        try {
            StorageResponse response = lockerService.storeItem(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(StorageResponse.builder()
                            .message("Error: " + e.getMessage())
                            .build());
        }
    }


    @PostMapping("/retrieve")
    public ResponseEntity<RetrieveResponse> retrieveItem(@RequestBody RetrieveRequest request) {
        try {
            RetrieveResponse response = lockerService.retrieveItem(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(RetrieveResponse.builder()
                    .lockerId(request.getLockerId())
                    .tokenValue(request.getTokenValue())
                    .message("Error: " + e.getMessage())
                    .build());
        }
    }

    @GetMapping("/{locationName}/status")
    public ResponseEntity<List<Locker>> getLockerStatus(@PathVariable String locationName) {
        return ResponseEntity.ok(lockerService.getLockersByLocation(locationName));
    }
}
