package com.a207.smartlocker.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// dto/RobotStatusDto.java
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RobotStatusDto {
    private int robotStatusId;    // Primary Key (Non-nullable)
    private String robotStatus;   // Non-nullable
}
