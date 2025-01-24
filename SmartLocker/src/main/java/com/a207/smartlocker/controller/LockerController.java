// LockerController.java
package com.a207.smartlocker.controller;

import com.a207.smartlocker.model.dto.RetrieveRequest;
import com.a207.smartlocker.model.dto.RetrieveResponse;
import com.a207.smartlocker.service.LockerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/lockers")
public class LockerController {

    @Autowired
    private LockerService lockerService;

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
