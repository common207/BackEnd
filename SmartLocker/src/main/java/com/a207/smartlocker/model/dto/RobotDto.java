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
    private int robotId;
    private String robotName;
    private int completedTasks;
    private LocalDateTime lastMaintenance;
    private int robotStatusId;
}
