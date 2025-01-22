package com.a207.smartlocker.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// dto/LockerLocationDto.java
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LockerLocationDto {
    private int locationId;        // Primary Key (Non-nullable)
    private String locationName;   // Non-nullable
    private int totalLockers;      // Non-nullable
    private int availableLockers;  // Non-nullable
}
