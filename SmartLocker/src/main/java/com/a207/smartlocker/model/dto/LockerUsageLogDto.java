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
    private int logId;                // Primary Key (Non-nullable)
    private int lockerId;             // Foreign Key (Non-nullable)
    private int userId;               // Foreign Key (Non-nullable)
    private LocalDateTime storeTime;  // Non-nullable
    private int storeRobotId;         // Foreign Key (Non-nullable)
    private LocalDateTime retrieveTime; // Nullable
    private Integer retrieveRobotId;  // Foreign Key (Nullable)
}