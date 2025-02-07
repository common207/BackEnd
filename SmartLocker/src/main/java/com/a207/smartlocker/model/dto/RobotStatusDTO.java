package com.a207.smartlocker.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RobotStatusDTO {
    private Long robotId;
    private String robotName;
    private Integer completedTasks;
    private LocalDateTime lastMaintenance;
    private String robotStatus;
}
