package com.a207.smartlocker.model.dto;

// dto/LockerUsageLogDto.java

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LockerUsageLogDto {
    private Long logId;                // Primary Key (Non-nullable)
    private Long lockerId;             // Foreign Key (Non-nullable)
    private Long userId;               // Foreign Key (Non-nullable)
    private LocalDateTime storeTime;  // Non-nullable
    private Long storeRobotId;         // Foreign Key (Non-nullable)
    private LocalDateTime retrieveTime; // Nullable
    private Long retrieveRobotId;  // Foreign Key (Nullable)
}