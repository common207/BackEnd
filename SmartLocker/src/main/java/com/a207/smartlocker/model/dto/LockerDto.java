package com.a207.smartlocker.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// dto/LockerDto.java
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LockerDto {
    private int lockerId;           // Primary Key (Non-nullable)
    private int lockerStatusId;     // Foreign Key (Non-nullable)
    private int lockerLocationId;   // Foreign Key (Non-nullable)
    private Integer tokenId;        // Foreign Key (Nullable)
}
