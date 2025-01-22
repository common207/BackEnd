package com.a207.smartlocker.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

// dto/RobotDto.java
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RobotDto {
    private Long robotId;              // Primary Key (Non-nullable)
    private String robotName;         // Non-nullable
    private Long completedTasks;       // Default 0
    private LocalDateTime lastMaintenance; // Non-nullable
    private Long robotStatusId;        // Foreign Key (Non-nullable)
}
