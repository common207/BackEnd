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
    private int logId;
    private int lockerId;
    private int userId;
    private LocalDateTime storeTime;
    private int storeRobotId;
    private LocalDateTime retrieveTime;
    private int retrieveRobotId;
}