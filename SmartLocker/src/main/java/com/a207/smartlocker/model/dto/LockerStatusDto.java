package com.a207.smartlocker.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// dto/LockerStatusDto.java
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LockerStatusDto {
    private int lockerStatusId;    // Primary Key (Non-nullable)
    private String lockerStatus;   // Non-nullable
}